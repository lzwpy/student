package com.petclass.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class RuleDtos {
    private RuleDtos() {
    }

    @Data
    public static class SaveRequest {
        @NotBlank(message = "规则名称不能为空")
        private String name;
        @NotBlank(message = "规则类型不能为空")
        private String type;
        private String icon;
        @NotNull(message = "经验值不能为空")
        private Integer expValue;
        @NotNull(message = "金币值不能为空")
        private Integer coinValue;
        private Integer sortOrder;
    }
}
