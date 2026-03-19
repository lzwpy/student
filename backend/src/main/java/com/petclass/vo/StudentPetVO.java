package com.petclass.vo;

import lombok.Data;

@Data
public class StudentPetVO {
    private Long studentId;
    private String studentName;
    private Integer sortOrder;
    private Long petId;
    private String petName;
    private String imageKey;
    private Integer level;
    private Integer exp;
    private Integer totalExp;
    private Integer coins;
}
