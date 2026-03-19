package com.petclass.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("pet")
public class Pet {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private String name;
    private String imageKey;
    private Integer level;
    private Integer exp;
    private Integer totalExp;
    private Integer coins;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
