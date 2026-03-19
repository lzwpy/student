package com.petclass.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("purchase_log")
public class PurchaseLog {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private Long shopItemId;
    private String itemName;
    private Integer price;
    private LocalDateTime createdAt;
}
