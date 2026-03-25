package com.petclass.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.petclass.entity.StudentCoin;

public interface StudentCoinService extends IService<StudentCoin> {
    StudentCoin getOrCreateByStudentId(Long studentId);

    void applyCoinChange(Long studentId, Integer coinDelta);

    void spendCoins(Long studentId, Integer amount);
}
