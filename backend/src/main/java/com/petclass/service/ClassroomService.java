package com.petclass.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.petclass.entity.Classroom;

import java.util.List;

public interface ClassroomService extends IService<Classroom> {
    List<Classroom> listByTeacher(Long teacherId);
}
