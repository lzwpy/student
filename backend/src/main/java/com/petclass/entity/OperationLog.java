package com.petclass.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("operation_log")
public class OperationLog {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long teacherId;
    private Long classroomId;
    private Long studentId;
    private String actionType;
    private Long refId;
    private String title;
    private String summary;
    private Integer expChange;
    private Integer coinChange;
    private LocalDateTime createdAt;
    private LocalDateTime revertedAt;
    private Long revertedBy;
}
