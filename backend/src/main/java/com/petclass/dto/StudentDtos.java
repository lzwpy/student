package com.petclass.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

public class StudentDtos {
    private StudentDtos() {
    }

    @Data
    public static class CreateRequest {
        @NotEmpty(message = "学生姓名不能为空")
        private List<@NotBlank(message = "学生姓名不能为空") String> names;
    }

    @Data
    public static class RenameRequest {
        @NotBlank(message = "学生姓名不能为空")
        private String name;
    }
}
