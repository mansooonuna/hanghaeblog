package com.sparta.hanhaeblog.controller;
import com.sparta.hanhaeblog.dto.LoginRequestDto;
import com.sparta.hanhaeblog.dto.SignupRequestDto;
import com.sparta.hanhaeblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@Valid SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public String login(LoginRequestDto loginRequestDto) {
        return userService.login(loginRequestDto);
    }
}
