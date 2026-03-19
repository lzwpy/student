package com.petclass.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    private SecurityUtils() {
    }

    public static Long getCurrentTeacherId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalArgumentException("未登录");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof AuthUser authUser) {
            return authUser.getUserId();
        }
        throw new IllegalArgumentException("无效登录状态");
    }
}
