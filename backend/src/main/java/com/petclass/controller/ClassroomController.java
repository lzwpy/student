package com.petclass.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.petclass.common.Result;
import com.petclass.common.SecurityUtils;
import com.petclass.dto.ClassroomDtos;
import com.petclass.entity.Classroom;
import com.petclass.service.ClassroomService;
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
@RequestMapping("/api/classrooms")
@RequiredArgsConstructor
public class ClassroomController {
    private final ClassroomService classroomService;

    @GetMapping
    public Result<List<Classroom>> list() {
        return Result.ok(classroomService.listByTeacher(SecurityUtils.getCurrentTeacherId()));
    }

    @PostMapping
    public Result<Void> create(@Valid @RequestBody ClassroomDtos.CreateRequest request) {
        Classroom classroom = new Classroom();
        classroom.setTeacherId(SecurityUtils.getCurrentTeacherId());
        classroom.setName(request.getName());
        classroomService.save(classroom);
        return Result.ok();
    }

    @PutMapping("/{id}")
    public Result<Void> rename(@PathVariable Long id, @Valid @RequestBody ClassroomDtos.RenameRequest request) {
        Long teacherId = SecurityUtils.getCurrentTeacherId();
        Classroom classroom = classroomService.getOne(new LambdaQueryWrapper<Classroom>()
            .eq(Classroom::getId, id).eq(Classroom::getTeacherId, teacherId));
        if (classroom == null) {
            throw new IllegalArgumentException("班级不存在");
        }
        classroom.setName(request.getName());
        classroomService.updateById(classroom);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        Long teacherId = SecurityUtils.getCurrentTeacherId();
        Classroom classroom = classroomService.getOne(new LambdaQueryWrapper<Classroom>()
            .eq(Classroom::getId, id).eq(Classroom::getTeacherId, teacherId));
        if (classroom == null) {
            throw new IllegalArgumentException("班级不存在");
        }
        classroomService.removeById(id);
        return Result.ok();
    }
}
