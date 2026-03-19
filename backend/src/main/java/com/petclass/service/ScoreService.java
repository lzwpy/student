package com.petclass.service;

import com.petclass.dto.ScoreDtos;

public interface ScoreService {
    void score(Long teacherId, ScoreDtos.ScoreRequest request);

    void batchScore(Long teacherId, ScoreDtos.BatchScoreRequest request);
}
