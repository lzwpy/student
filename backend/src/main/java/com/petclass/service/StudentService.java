package com.petclass.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.petclass.entity.Student;
import com.petclass.vo.StudentPetVO;

import java.util.List;

public interface StudentService extends IService<Student> {
    List<StudentPetVO> listStudentWithPetByClassId(Long classId);
}
