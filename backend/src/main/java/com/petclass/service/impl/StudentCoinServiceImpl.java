package com.petclass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petclass.entity.StudentCoin;
import com.petclass.mapper.StudentCoinMapper;
import com.petclass.service.StudentCoinService;
import org.springframework.stereotype.Service;

@Service
public class StudentCoinServiceImpl extends ServiceImpl<StudentCoinMapper, StudentCoin> implements StudentCoinService {

    @Override
    public StudentCoin getOrCreateByStudentId(Long studentId) {
        StudentCoin studentCoin = getOne(new LambdaQueryWrapper<StudentCoin>()
            .eq(StudentCoin::getStudentId, studentId));
        if (studentCoin != null) {
            return studentCoin;
        }

        studentCoin = new StudentCoin();
        studentCoin.setStudentId(studentId);
        studentCoin.setCoins(0);
        save(studentCoin);
        return studentCoin;
    }

    @Override
    public void applyCoinChange(Long studentId, Integer coinDelta) {
        StudentCoin studentCoin = getOrCreateByStudentId(studentId);
        int nextCoins = Math.max(0, studentCoin.getCoins() + coinDelta);
        studentCoin.setCoins(nextCoins);
        updateById(studentCoin);
    }

    @Override
    public void spendCoins(Long studentId, Integer amount) {
        StudentCoin studentCoin = getOrCreateByStudentId(studentId);
        if (studentCoin.getCoins() < amount) {
            throw new IllegalArgumentException("金币不足");
        }
        studentCoin.setCoins(studentCoin.getCoins() - amount);
        updateById(studentCoin);
    }
}
