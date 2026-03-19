package com.petclass.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class ShopDtos {
    private ShopDtos() {
    }

    @Data
    public static class SaveItemRequest {
        @NotBlank(message = "商品名称不能为空")
        private String name;
        private String description;
        private String image;
        @NotNull(message = "价格不能为空")
        private Integer price;
        private Integer stock;
        private Integer status;
    }

    @Data
    public static class PurchaseRequest {
        @NotNull(message = "学生ID不能为空")
        private Long studentId;
        @NotNull(message = "商品ID不能为空")
        private Long itemId;
    }
}
