package com.petclass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petclass.entity.Classroom;
import com.petclass.mapper.ClassroomMapper;
import com.petclass.service.ClassroomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomServiceImpl extends ServiceImpl<ClassroomMapper, Classroom> implements ClassroomService {
    @Override
    public List<Classroom> listByTeacher(Long teacherId) {
        return list(new LambdaQueryWrapper<Classroom>()
            .eq(Classroom::getTeacherId, teacherId)
            .orderByAsc(Classroom::getId));
    }
}
