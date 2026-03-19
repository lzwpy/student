package com.petclass.controller;

import com.petclass.common.Result;
import com.petclass.common.SecurityUtils;
import com.petclass.dto.ShopDtos;
import com.petclass.entity.ShopItem;
import com.petclass.service.ShopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shop")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @GetMapping("/items")
    public Result<List<ShopItem>> list() {
        return Result.ok(shopService.listByTeacher(SecurityUtils.getCurrentTeacherId()));
    }

    @PostMapping("/items")
    public Result<Void> create(@Valid @RequestBody ShopDtos.SaveItemRequest request) {
        ShopItem item = new ShopItem();
        item.setTeacherId(SecurityUtils.getCurrentTeacherId());
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setImage(request.getImage());
        item.setPrice(request.getPrice());
        item.setStock(request.getStock() == null ? -1 : request.getStock());
        item.setStatus(request.getStatus() == null ? 1 : request.getStatus());
        shopService.save(item);
        return Result.ok();
    }

    @PutMapping("/items/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody ShopDtos.SaveItemRequest request) {
        ShopItem item = shopService.getById(id);
        if (item == null || !item.getTeacherId().equals(SecurityUtils.getCurrentTeacherId())) {
            throw new IllegalArgumentException("商品不存在");
        }
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setImage(request.getImage());
        item.setPrice(request.getPrice());
        item.setStock(request.getStock() == null ? -1 : request.getStock());
        item.setStatus(request.getStatus() == null ? 1 : request.getStatus());
        shopService.updateById(item);
        return Result.ok();
    }

    @DeleteMapping("/items/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        ShopItem item = shopService.getById(id);
        if (item == null || !item.getTeacherId().equals(SecurityUtils.getCurrentTeacherId())) {
            throw new IllegalArgumentException("商品不存在");
        }
        item.setStatus(0);
        shopService.updateById(item);
        return Result.ok();
    }

    @PostMapping("/purchase")
    public Result<Void> purchase(@Valid @RequestBody ShopDtos.PurchaseRequest request) {
        shopService.purchase(SecurityUtils.getCurrentTeacherId(), request);
        return Result.ok();
    }
}
