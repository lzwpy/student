package com.petclass.vo;

import lombok.Data;

@Data
public class LeaderboardItemVO {
    private Long studentId;
    private String studentName;
    private String petName;
    private String imageKey;
    private Integer level;
    private Integer totalExp;
    private Integer coins;
}
