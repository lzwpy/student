package com.petclass.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.petclass.entity.Pet;

public interface PetService extends IService<Pet> {
    void applyScore(Long studentId, Integer expDelta, Integer coinDelta);
}