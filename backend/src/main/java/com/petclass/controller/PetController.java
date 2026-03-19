package com.petclass.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.petclass.common.Result;
import com.petclass.dto.PetDtos;
import com.petclass.entity.Pet;
import com.petclass.service.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @PostMapping("/students/{studentId}/pet")
    public Result<Void> adopt(@PathVariable Long studentId, @Valid @RequestBody PetDtos.AdoptRequest request) {
        Pet existing = petService.getOne(new LambdaQueryWrapper<Pet>().eq(Pet::getStudentId, studentId));
        if (existing != null) {
            throw new IllegalArgumentException("该学生已经领养过宠物");
        }
        Pet pet = new Pet();
        pet.setStudentId(studentId);
        pet.setName(request.getPetName());
        pet.setImageKey(request.getImageKey());
        pet.setLevel(1);
        pet.setExp(0);
        pet.setTotalExp(0);
        pet.setCoins(0);
        petService.save(pet);
        return Result.ok();
    }

    @GetMapping("/students/{studentId}/pet")
    public Result<Pet> detail(@PathVariable Long studentId) {
        Pet pet = petService.getOne(new LambdaQueryWrapper<Pet>().eq(Pet::getStudentId, studentId));
        return Result.ok(pet);
    }

    @PutMapping("/pets/{id}/name")
    public Result<Void> rename(@PathVariable Long id, @Valid @RequestBody PetDtos.RenameRequest request) {
        Pet pet = petService.getById(id);
        if (pet == null) {
            throw new IllegalArgumentException("宠物不存在");
        }
        pet.setName(request.getPetName());
        petService.updateById(pet);
        return Result.ok();
    }
}
