package com.petclass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petclass.dto.ShopDtos;
import com.petclass.entity.Pet;
import com.petclass.entity.PurchaseLog;
import com.petclass.entity.ShopItem;
import com.petclass.entity.Student;
import com.petclass.mapper.PetMapper;
import com.petclass.mapper.PurchaseLogMapper;
import com.petclass.mapper.ShopItemMapper;
import com.petclass.mapper.StudentMapper;
import com.petclass.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl extends ServiceImpl<ShopItemMapper, ShopItem> implements ShopService {
    private final StudentMapper studentMapper;
    private final PetMapper petMapper;
    private final PurchaseLogMapper purchaseLogMapper;

    @Override
    public List<ShopItem> listByTeacher(Long teacherId) {
        return list(new LambdaQueryWrapper<ShopItem>()
            .eq(ShopItem::getTeacherId, teacherId)
            .orderByDesc(ShopItem::getId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void purchase(Long teacherId, ShopDtos.PurchaseRequest request) {
        ShopItem item = getById(request.getItemId());
        if (item == null || !item.getTeacherId().equals(teacherId) || item.getStatus() == 0) {
            throw new IllegalArgumentException("商品不存在或已下架");
        }

        Student student = studentMapper.selectById(request.getStudentId());
        if (student == null) {
            throw new IllegalArgumentException("学生不存在");
        }
        Pet pet = petMapper.selectOne(new LambdaQueryWrapper<Pet>().eq(Pet::getStudentId, student.getId()));
        if (pet == null) {
            throw new IllegalArgumentException("该学生尚未领养宠物");
        }
        if (pet.getCoins() < item.getPrice()) {
            throw new IllegalArgumentException("金币不足");
        }
        if (item.getStock() != null && item.getStock() >= 0 && item.getStock() <= 0) {
            throw new IllegalArgumentException("库存不足");
        }

        pet.setCoins(pet.getCoins() - item.getPrice());
        petMapper.updateById(pet);

        if (item.getStock() != null && item.getStock() > 0) {
            item.setStock(item.getStock() - 1);
            updateById(item);
        }

        PurchaseLog log = new PurchaseLog();
        log.setStudentId(student.getId());
        log.setShopItemId(item.getId());
        log.setItemName(item.getName());
        log.setPrice(item.getPrice());
        purchaseLogMapper.insert(log);
    }
}
