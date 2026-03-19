package com.petclass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.petclass.common.JwtUtils;
import com.petclass.dto.AuthDtos;
import com.petclass.entity.Teacher;
import com.petclass.mapper.TeacherMapper;
import com.petclass.service.AuthService;
import com.petclass.vo.AuthLoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final TeacherMapper teacherMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public AuthLoginVO login(AuthDtos.LoginRequest request) {
        Teacher teacher = teacherMapper.selectOne(new LambdaQueryWrapper<Teacher>()
            .eq(Teacher::getUsername, request.getUsername()));
        if (teacher == null || !passwordEncoder.matches(request.getPassword(), teacher.getPasswordHash())) {
            throw new IllegalArgumentException("用户名或密码错误");
        }
        String token = jwtUtils.generateToken(teacher.getId(), teacher.getUsername());
        return new AuthLoginVO(token, teacher.getId(), teacher.getUsername(), teacher.getNickname());
    }

    @Override
    public void register(AuthDtos.RegisterRequest request) {
        Long count = teacherMapper.selectCount(new LambdaQueryWrapper<Teacher>()
            .eq(Teacher::getUsername, request.getUsername()));
        if (count != null && count > 0) {
            throw new IllegalArgumentException("用户名已存在");
        }
        Teacher teacher = new Teacher();
        teacher.setUsername(request.getUsername());
        teacher.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        teacher.setNickname(request.getNickname() == null ? request.getUsername() : request.getNickname());
        teacherMapper.insert(teacher);
    }

    @Override
    public void changePassword(Long teacherId, AuthDtos.ChangePasswordRequest request) {
        Teacher teacher = teacherMapper.selectById(teacherId);
        if (teacher == null) {
            throw new IllegalArgumentException("账号不存在");
        }
        if (!passwordEncoder.matches(request.getOldPassword(), teacher.getPasswordHash())) {
            throw new IllegalArgumentException("旧密码错误");
        }
        teacher.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        teacherMapper.updateById(teacher);
    }
}
