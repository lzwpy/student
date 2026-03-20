package com.petclass.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.petclass.entity.Pet;

public interface PetService extends IService<Pet> {
    void applyExpChange(Long studentId, Integer expDelta);
}