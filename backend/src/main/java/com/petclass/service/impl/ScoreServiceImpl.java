package com.petclass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.petclass.dto.ScoreDtos;
import com.petclass.entity.Rule;
import com.petclass.entity.ScoreLog;
import com.petclass.entity.Student;
import com.petclass.mapper.RuleMapper;
import com.petclass.mapper.ScoreLogMapper;
import com.petclass.mapper.StudentMapper;
import com.petclass.service.PetService;
import com.petclass.service.ScoreService;
import com.petclass.service.StudentCoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {
    private final RuleMapper ruleMapper;
    private final StudentMapper studentMapper;
    private final ScoreLogMapper scoreLogMapper;
    private final PetService petService;
    private final StudentCoinService studentCoinService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void score(Long teacherId, ScoreDtos.ScoreRequest request) {
        Rule rule = ruleMapper.selectById(request.getRuleId());
        if (rule == null || !rule.getTeacherId().equals(teacherId)) {
            throw new IllegalArgumentException("规则不存在");
        }
        Student student = studentMapper.selectById(request.getStudentId());
        if (student == null) {
            throw new IllegalArgumentException("学生不存在");
        }

        petService.applyExpChange(student.getId(), rule.getExpValue());
        studentCoinService.applyCoinChange(student.getId(), rule.getCoinValue());

        ScoreLog log = new ScoreLog();
        log.setStudentId(student.getId());
        log.setClassroomId(student.getClassroomId());
        log.setRuleId(rule.getId());
        log.setRuleName(rule.getName());
        log.setExpChange(rule.getExpValue());
        log.setCoinChange(rule.getCoinValue());
        log.setOperatorId(teacherId);
        scoreLogMapper.insert(log);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchScore(Long teacherId, ScoreDtos.BatchScoreRequest request) {
        for (Long studentId : request.getStudentIds()) {
            ScoreDtos.ScoreRequest scoreRequest = new ScoreDtos.ScoreRequest();
            scoreRequest.setStudentId(studentId);
            scoreRequest.setRuleId(request.getRuleId());
            score(teacherId, scoreRequest);
        }
    }
}
