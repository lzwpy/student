package com.petclass.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperationVO {
    private Long id;
    private String actionType;
    private Long studentId;
    private String studentName;
    private String title;
    private String summary;
    private Integer expChange;
    private Integer coinChange;
    private LocalDateTime createdAt;
    private LocalDateTime revertedAt;
    private boolean reverted;
    private boolean revertible;
}
