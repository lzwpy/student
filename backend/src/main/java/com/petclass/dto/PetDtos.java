package com.petclass.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class PetDtos {
    private PetDtos() {
    }

    @Data
    public static class AdoptRequest {
        @NotBlank(message = "宠物名称不能为空")
        private String petName;
        @NotBlank(message = "宠物形象不能为空")
        private String imageKey;
    }

    @Data
    public static class RenameRequest {
        @NotBlank(message = "宠物名称不能为空")
        private String petName;
    }
}
