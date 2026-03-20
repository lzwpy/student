package com.petclass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.petclass.entity.Pet;
import com.petclass.entity.Student;
import com.petclass.entity.StudentCoin;
import com.petclass.mapper.PetMapper;
import com.petclass.mapper.StudentMapper;
import com.petclass.mapper.StudentCoinMapper;
import com.petclass.service.LeaderboardService;
import com.petclass.vo.LeaderboardItemVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LeaderboardServiceImpl implements LeaderboardService {
    private final StudentMapper studentMapper;
    private final PetMapper petMapper;
    private final StudentCoinMapper studentCoinMapper;

    @Override
    public List<LeaderboardItemVO> petRank(Long classId) {
        List<LeaderboardItemVO> list = buildPetRankList(classId);
        list.sort(Comparator.comparing(LeaderboardItemVO::getTotalExp, Comparator.nullsFirst(Integer::compareTo))
            .reversed()
            .thenComparing(LeaderboardItemVO::getStudentId));
        return list;
    }

    @Override
    public List<LeaderboardItemVO> coinRank(Long classId) {
        List<LeaderboardItemVO> list = buildCoinRankList(classId);
        list.sort(Comparator.comparing(LeaderboardItemVO::getCoins, Comparator.nullsFirst(Integer::compareTo))
            .reversed()
            .thenComparing(LeaderboardItemVO::getStudentId));
        return list;
    }

    private List<LeaderboardItemVO> buildPetRankList(Long classId) {
        List<Student> students = studentMapper.selectList(new LambdaQueryWrapper<Student>()
            .eq(Student::getClassroomId, classId));
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
        List<LeaderboardItemVO> result = new ArrayList<>();
        for (Student student : students) {
            Pet pet = petMap.get(student.getId());
            if (pet == null) {
                continue;
            }
            StudentCoin studentCoin = coinMap.get(student.getId());
            LeaderboardItemVO vo = new LeaderboardItemVO();
            vo.setStudentId(student.getId());
            vo.setStudentName(student.getName());
            vo.setPetName(pet.getName());
            vo.setImageKey(pet.getImageKey());
            vo.setLevel(pet.getLevel());
            vo.setTotalExp(pet.getTotalExp());
            vo.setCoins(studentCoin == null ? 0 : studentCoin.getCoins());
            result.add(vo);
        }
        return result;
    }

    private List<LeaderboardItemVO> buildCoinRankList(Long classId) {
        List<Student> students = studentMapper.selectList(new LambdaQueryWrapper<Student>()
            .eq(Student::getClassroomId, classId));
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

        List<LeaderboardItemVO> result = new ArrayList<>();
        for (Student student : students) {
            Pet pet = petMap.get(student.getId());
            StudentCoin studentCoin = coinMap.get(student.getId());

            LeaderboardItemVO vo = new LeaderboardItemVO();
            vo.setStudentId(student.getId());
            vo.setStudentName(student.getName());
            vo.setPetName(pet == null ? null : pet.getName());
            vo.setImageKey(pet == null ? null : pet.getImageKey());
            vo.setLevel(pet == null ? null : pet.getLevel());
            vo.setTotalExp(pet == null ? null : pet.getTotalExp());
            vo.setCoins(studentCoin == null ? 0 : studentCoin.getCoins());
            result.add(vo);
        }
        return result;
    }
}
