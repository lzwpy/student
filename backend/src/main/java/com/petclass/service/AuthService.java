package com.petclass.service;

import com.petclass.dto.AuthDtos;
import com.petclass.vo.AuthLoginVO;

public interface AuthService {
    AuthLoginVO login(AuthDtos.LoginRequest request);

    void register(AuthDtos.RegisterRequest request);

    void changePassword(Long teacherId, AuthDtos.ChangePasswordRequest request);
}
