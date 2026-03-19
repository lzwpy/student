package com.petclass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petclass.entity.Pet;
import com.petclass.mapper.PetMapper;
import com.petclass.service.PetService;
import org.springframework.stereotype.Service;

@Service
public class PetServiceImpl extends ServiceImpl<PetMapper, Pet> implements PetService {

    @Override
    public void applyScore(Long studentId, Integer expDelta, Integer coinDelta) {
        Pet pet = getOne(new LambdaQueryWrapper<Pet>().eq(Pet::getStudentId, studentId));
        if (pet == null) {
            throw new IllegalArgumentException("该学生尚未领养宠物");
        }
        int totalExp = Math.max(0, pet.getTotalExp() + expDelta);
        int coins = Math.max(0, pet.getCoins() + coinDelta);
        int level = calculateLevel(totalExp);
        int currentExp = calculateCurrentLevelExp(totalExp, level);
        pet.setTotalExp(totalExp);
        pet.setCoins(coins);
        pet.setLevel(level);
        pet.setExp(currentExp);
        updateById(pet);
    }

    private int calculateLevel(int totalExp) {
        if (totalExp >= 100) {
            return 5;
        }
        if (totalExp >= 50) {
            return 4;
        }
        if (totalExp >= 25) {
            return 3;
        }
        if (totalExp >= 10) {
            return 2;
        }
        return 1;
    }

    private int calculateCurrentLevelExp(int totalExp, int level) {
        return switch (level) {
            case 5 -> totalExp - 100;
            case 4 -> totalExp - 50;
            case 3 -> totalExp - 25;
            case 2 -> totalExp - 10;
            default -> totalExp;
        };
    }
}
