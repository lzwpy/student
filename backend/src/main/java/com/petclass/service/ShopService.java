package com.petclass.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.petclass.dto.ShopDtos;
import com.petclass.entity.ShopItem;

import java.util.List;

public interface ShopService extends IService<ShopItem> {
    List<ShopItem> listByTeacher(Long teacherId);

    void purchase(Long teacherId, ShopDtos.PurchaseRequest request);
}
