package com.petclass.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.petclass.common.Result;
import com.petclass.dto.StudentDtos;
import com.petclass.entity.Student;
import com.petclass.service.StudentService;
import com.petclass.service.StudentCoinService;
import com.petclass.vo.StudentPetVO;
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
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final StudentCoinService studentCoinService;

    @GetMapping("/api/classrooms/{classId}/students")
    public Result<List<StudentPetVO>> list(@PathVariable Long classId) {
        return Result.ok(studentService.listStudentWithPetByClassId(classId));
    }

    @PostMapping("/api/classrooms/{classId}/students")
    public Result<Void> create(@PathVariable Long classId, @Valid @RequestBody StudentDtos.CreateRequest request) {
        Integer maxOrder = studentService.list(new LambdaQueryWrapper<Student>()
                .eq(Student::getClassroomId, classId))
            .stream().map(Student::getSortOrder).filter(v -> v != null).max(Integer::compareTo).orElse(0);

        int nextOrder = maxOrder + 1;
        for (String name : request.getNames()) {
            Student student = new Student();
            student.setClassroomId(classId);
            student.setName(name);
            student.setSortOrder(nextOrder++);
            studentService.save(student);
            studentCoinService.getOrCreateByStudentId(student.getId());
        }
        return Result.ok();
    }

    @PutMapping("/api/students/{id}")
    public Result<Void> rename(@PathVariable Long id, @Valid @RequestBody StudentDtos.RenameRequest request) {
        Student student = studentService.getById(id);
        if (student == null) {
            throw new IllegalArgumentException("学生不存在");
        }
        student.setName(request.getName());
        studentService.updateById(student);
        return Result.ok();
    }

    @DeleteMapping("/api/students/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        studentService.removeById(id);
        return Result.ok();
    }
}
