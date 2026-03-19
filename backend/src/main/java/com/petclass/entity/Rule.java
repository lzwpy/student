package com.petclass.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("rule")
public class Rule {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long teacherId;
    private String name;
    private String type;
    private String icon;
    private Integer expValue;
    private Integer coinValue;
    private Integer sortOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
