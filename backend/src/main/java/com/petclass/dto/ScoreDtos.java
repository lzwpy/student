package com.petclass.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

public class ScoreDtos {
    private ScoreDtos() {
    }

    @Data
    public static class ScoreRequest {
        @NotNull(message = "学生ID不能为空")
        private Long studentId;
        @NotNull(message = "规则ID不能为空")
        private Long ruleId;
    }

    @Data
    public static class BatchScoreRequest {
        @NotEmpty(message = "学生ID不能为空")
        private List<Long> studentIds;
        @NotNull(message = "规则ID不能为空")
        private Long ruleId;
    }
}
