package com.petclass.controller;

import com.petclass.common.Result;
import com.petclass.common.SecurityUtils;
import com.petclass.dto.ScoreDtos;
import com.petclass.service.ScoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/score")
@RequiredArgsConstructor
public class ScoreController {
    private final ScoreService scoreService;

    @PostMapping
    public Result<Void> score(@Valid @RequestBody ScoreDtos.ScoreRequest request) {
        scoreService.score(SecurityUtils.getCurrentTeacherId(), request);
        return Result.ok();
    }

    @PostMapping("/batch")
    public Result<Void> batch(@Valid @RequestBody ScoreDtos.BatchScoreRequest request) {
        scoreService.batchScore(SecurityUtils.getCurrentTeacherId(), request);
        return Result.ok();
    }
}
