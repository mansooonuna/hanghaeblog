package com.sparta.hanhaeblog.service;

import com.sparta.hanhaeblog.dto.LoginRequestDto;
import com.sparta.hanhaeblog.dto.SignupRequestDto;
import com.sparta.hanhaeblog.entity.User;
import com.sparta.hanhaeblog.jwt.JwtUtil;
import com.sparta.hanhaeblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // 회원가입
    @Transactional
    public String signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();

        // username은 알파벳 소문자, 숫자
        if(!Pattern.matches("^[a-z0-9]*$", username)) {
            return "아이디는 알파벳 소문자, 숫자로 작성해주세요.";
        }
        // password는 알파벳 대소문자, 숫자
        if(!Pattern.matches("^[a-zA-Z0-9]*$", password)) {
            return "비밀번호는 알파벳 대소문자, 숫자로 작성해주세요.";
        }
        else {
            // 회원 중복 확인
            Optional<User> found = userRepository.findByUsername(username);
            if (found.isPresent()) {
                throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
            }

            User user = new User(username, password);
            userRepository.save(user);
            return "회원가입 성공";
        }
    }

    // 회원 로그인
    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인
        if(!user.getPassword().equals(password)){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰을 생성하고 HTTP 응답 해더에 추가하는 부분
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));
        // JwtUtil.AUTHORIZATION_HEADER : 토큰이 포함된 HTTP 헤더의 이름을 상수로 지정한 것
        // 이를 통해 클라이언트 측에서 JWT 토큰을 전달받을 수 있음

        // jwtUtil.createToken(user.getUsername()) :사용자의 username을 기반으로 토큰을 만든다.

        // response.addHeader() : 이렇게 생성된 JWT 토큰을 HTTP 응답 헤더에 추가하겠다.
        // 이를 통해 인증된 사용자임을 서버에 전달할 수 있다.
    }
}
