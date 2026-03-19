package com.petclass.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.petclass.common.Result;
import com.petclass.dto.LogQueryDto;
import com.petclass.entity.ScoreLog;
import com.petclass.mapper.ScoreLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class LogController {
    private final ScoreLogMapper scoreLogMapper;

    @GetMapping
    public Result<List<ScoreLog>> list(LogQueryDto query) {
        LambdaQueryWrapper<ScoreLog> wrapper = new LambdaQueryWrapper<>();
        if (query.getClassId() != null) {
            wrapper.eq(ScoreLog::getClassroomId, query.getClassId());
        }
        if (query.getStartDate() != null) {
            wrapper.ge(ScoreLog::getCreatedAt, query.getStartDate().atStartOfDay());
        }
        if (query.getEndDate() != null) {
            wrapper.le(ScoreLog::getCreatedAt, LocalDateTime.of(query.getEndDate(), java.time.LocalTime.MAX));
        }
        wrapper.orderByDesc(ScoreLog::getCreatedAt);
        return Result.ok(scoreLogMapper.selectList(wrapper));
    }
}
