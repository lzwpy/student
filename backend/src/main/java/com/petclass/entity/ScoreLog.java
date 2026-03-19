package com.petclass.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("score_log")
public class ScoreLog {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private Long classroomId;
    private Long ruleId;
    private String ruleName;
    private Integer expChange;
    private Integer coinChange;
    private Long operatorId;
    private LocalDateTime createdAt;
}
