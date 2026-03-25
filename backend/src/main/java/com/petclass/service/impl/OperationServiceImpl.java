package com.petclass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.petclass.dto.OperationQueryDto;
import com.petclass.entity.OperationLog;
import com.petclass.entity.PurchaseLog;
import com.petclass.entity.ScoreLog;
import com.petclass.entity.ShopItem;
import com.petclass.entity.Student;
import com.petclass.mapper.OperationLogMapper;
import com.petclass.mapper.PurchaseLogMapper;
import com.petclass.mapper.ScoreLogMapper;
import com.petclass.mapper.ShopItemMapper;
import com.petclass.mapper.StudentMapper;
import com.petclass.service.OperationService;
import com.petclass.service.PetService;
import com.petclass.service.StudentCoinService;
import com.petclass.vo.OperationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {
    private final OperationLogMapper operationLogMapper;
    private final ScoreLogMapper scoreLogMapper;
    private final PurchaseLogMapper purchaseLogMapper;
    private final ShopItemMapper shopItemMapper;
    private final StudentMapper studentMapper;
    private final PetService petService;
    private final StudentCoinService studentCoinService;

    @Override
    public void createScoreOperation(
        Long teacherId,
        Long classroomId,
        Long studentId,
        Long scoreLogId,
        String title,
        Integer expChange,
        Integer coinChange
    ) {
        OperationLog log = new OperationLog();
        log.setTeacherId(teacherId);
        log.setClassroomId(classroomId);
        log.setStudentId(studentId);
        log.setActionType("SCORE");
        log.setRefId(scoreLogId);
        log.setTitle(title);
        log.setSummary(buildScoreSummary(expChange, coinChange));
        log.setExpChange(expChange);
        log.setCoinChange(coinChange);
        operationLogMapper.insert(log);
    }

    @Override
    public void createPurchaseOperation(
        Long teacherId,
        Long classroomId,
        Long studentId,
        Long purchaseLogId,
        String itemName,
        Integer price
    ) {
        OperationLog log = new OperationLog();
        log.setTeacherId(teacherId);
        log.setClassroomId(classroomId);
        log.setStudentId(studentId);
        log.setActionType("PURCHASE");
        log.setRefId(purchaseLogId);
        log.setTitle("购买了" + itemName);
        log.setSummary("消耗金币 " + price);
        log.setExpChange(0);
        log.setCoinChange(-price);
        operationLogMapper.insert(log);
    }

    @Override
    public List<OperationVO> listRecent(Long teacherId, OperationQueryDto query) {
        int limit = query.getLimit() == null ? 20 : Math.max(1, Math.min(query.getLimit(), 100));
        LambdaQueryWrapper<OperationLog> wrapper = new LambdaQueryWrapper<OperationLog>()
            .eq(OperationLog::getTeacherId, teacherId)
            .orderByDesc(OperationLog::getCreatedAt)
            .last("LIMIT " + limit);
        if (query.getClassId() != null) {
            wrapper.eq(OperationLog::getClassroomId, query.getClassId());
        }
        if (query.getStudentId() != null) {
            wrapper.eq(OperationLog::getStudentId, query.getStudentId());
        }
        if (query.getStartDate() != null) {
            wrapper.ge(OperationLog::getCreatedAt, query.getStartDate().atStartOfDay());
        }
        if (query.getEndDate() != null) {
            wrapper.le(OperationLog::getCreatedAt, LocalDateTime.of(query.getEndDate(), LocalTime.MAX));
        }
        List<OperationLog> logs = operationLogMapper.selectList(wrapper);
        if (logs.isEmpty()) {
            return Collections.emptyList();
        }
        Map<Long, String> studentNameMap = studentMapper.selectBatchIds(
            logs.stream().map(OperationLog::getStudentId).distinct().toList()
        ).stream().collect(Collectors.toMap(Student::getId, Student::getName));

        return logs.stream().map(item -> {
            OperationVO vo = new OperationVO();
            vo.setId(item.getId());
            vo.setActionType(item.getActionType());
            vo.setStudentId(item.getStudentId());
            vo.setStudentName(studentNameMap.getOrDefault(item.getStudentId(), "未知学生"));
            vo.setTitle(item.getTitle());
            vo.setSummary(item.getSummary());
            vo.setExpChange(item.getExpChange());
            vo.setCoinChange(item.getCoinChange());
            vo.setCreatedAt(item.getCreatedAt());
            vo.setRevertedAt(item.getRevertedAt());
            vo.setReverted(item.getRevertedAt() != null);
            vo.setRevertible(item.getRevertedAt() == null);
            return vo;
        }).toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void revertOperation(Long teacherId, Long operationId) {
        OperationLog operation = operationLogMapper.selectById(operationId);
        if (operation == null || !teacherId.equals(operation.getTeacherId())) {
            throw new IllegalArgumentException("操作记录不存在");
        }
        if (operation.getRevertedAt() != null) {
            throw new IllegalArgumentException("该记录已撤回");
        }
        switch (operation.getActionType()) {
            case "SCORE" -> revertScore(operation);
            case "PURCHASE" -> revertPurchase(operation);
            default -> throw new IllegalArgumentException("暂不支持撤回该记录");
        }
        operation.setRevertedAt(LocalDateTime.now());
        operation.setRevertedBy(teacherId);
        operationLogMapper.updateById(operation);
    }

    private void revertScore(OperationLog operation) {
        ScoreLog scoreLog = scoreLogMapper.selectById(operation.getRefId());
        if (scoreLog == null) {
            throw new IllegalArgumentException("原始打分记录不存在");
        }
        petService.applyExpChange(scoreLog.getStudentId(), -scoreLog.getExpChange());
        reverseCoinChange(scoreLog.getStudentId(), scoreLog.getCoinChange());
    }

    private void revertPurchase(OperationLog operation) {
        PurchaseLog purchaseLog = purchaseLogMapper.selectById(operation.getRefId());
        if (purchaseLog == null) {
            throw new IllegalArgumentException("原始购买记录不存在");
        }
        studentCoinService.applyCoinChange(purchaseLog.getStudentId(), purchaseLog.getPrice());
        ShopItem item = shopItemMapper.selectById(purchaseLog.getShopItemId());
        if (item != null && item.getStock() != null && item.getStock() >= 0) {
            item.setStock(item.getStock() + 1);
            shopItemMapper.updateById(item);
        }
    }

    private void reverseCoinChange(Long studentId, Integer coinChange) {
        if (coinChange == null || coinChange == 0) {
            return;
        }
        if (coinChange > 0) {
            studentCoinService.spendCoins(studentId, coinChange);
            return;
        }
        studentCoinService.applyCoinChange(studentId, -coinChange);
    }

    private String buildScoreSummary(Integer expChange, Integer coinChange) {
        return buildSignedLabel("EXP", expChange) + " ｜ " + buildSignedLabel("金币", coinChange);
    }

    private String buildSignedLabel(String label, Integer value) {
        int safeValue = value == null ? 0 : value;
        if (safeValue > 0) {
            return label + " +" + safeValue;
        }
        return label + " " + safeValue;
    }
}
