package com.sparta.hanhaeblog.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {
    private String userId;
    private String username;
    private String password;
}