package com.sparta.hanhaeblog.controller;
import com.sparta.hanhaeblog.dto.LoginRequestDto;
import com.sparta.hanhaeblog.dto.SignupRequestDto;
import com.sparta.hanhaeblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@Valid SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    @ResponseBody
    @PostMapping("/login")
    public String login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return "로그인 성공";
    }
}
