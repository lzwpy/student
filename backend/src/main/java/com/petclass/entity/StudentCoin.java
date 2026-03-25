package com.petclass.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("student_coin")
public class StudentCoin {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private Integer coins;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
