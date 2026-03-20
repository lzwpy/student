package com.petclass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petclass.entity.Pet;
import com.petclass.entity.Student;
import com.petclass.entity.StudentCoin;
import com.petclass.mapper.PetMapper;
import com.petclass.mapper.StudentMapper;
import com.petclass.mapper.StudentCoinMapper;
import com.petclass.service.StudentService;
import com.petclass.vo.StudentPetVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    private final PetMapper petMapper;
    private final StudentCoinMapper studentCoinMapper;

    @Override
    public List<StudentPetVO> listStudentWithPetByClassId(Long classId) {
        List<Student> students = list(new LambdaQueryWrapper<Student>()
            .eq(Student::getClassroomId, classId)
            .orderByAsc(Student::getSortOrder).orderByAsc(Student::getId));
        if (students.isEmpty()) {
            return new ArrayList<>();
        }

        List<Long> studentIds = students.stream().map(Student::getId).toList();
        List<Pet> pets = petMapper.selectList(new LambdaQueryWrapper<Pet>().in(Pet::getStudentId, studentIds));
        List<StudentCoin> studentCoins = studentCoinMapper.selectList(new LambdaQueryWrapper<StudentCoin>()
            .in(StudentCoin::getStudentId, studentIds));
        Map<Long, Pet> petMap = new HashMap<>();
        for (Pet pet : pets) {
            petMap.put(pet.getStudentId(), pet);
        }
        Map<Long, StudentCoin> coinMap = new HashMap<>();
        for (StudentCoin studentCoin : studentCoins) {
            coinMap.put(studentCoin.getStudentId(), studentCoin);
        }

        List<StudentPetVO> result = new ArrayList<>();
        for (Student student : students) {
            StudentPetVO vo = new StudentPetVO();
            vo.setStudentId(student.getId());
            vo.setStudentName(student.getName());
            vo.setSortOrder(student.getSortOrder());
            StudentCoin studentCoin = coinMap.get(student.getId());
            vo.setCoins(studentCoin == null ? 0 : studentCoin.getCoins());
            Pet pet = petMap.get(student.getId());
            if (pet != null) {
                vo.setPetId(pet.getId());
                vo.setPetName(pet.getName());
                vo.setImageKey(pet.getImageKey());
                vo.setLevel(pet.getLevel());
                vo.setExp(pet.getExp());
                vo.setTotalExp(pet.getTotalExp());
            }
            result.add(vo);
        }
        return result;
    }
}
