package com.petclass.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthLoginVO {
    private String token;
    private Long teacherId;
    private String username;
    private String nickname;
}
