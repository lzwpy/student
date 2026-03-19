package com.petclass.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class ClassroomDtos {
    private ClassroomDtos() {
    }

    @Data
    public static class CreateRequest {
        @NotBlank(message = "班级名称不能为空")
        private String name;
    }

    @Data
    public static class RenameRequest {
        @NotBlank(message = "班级名称不能为空")
        private String name;
    }
}
