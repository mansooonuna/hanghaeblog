package com.sparta.hanhaeblog.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
// user 는 이제 예약어라서 사용못함 so, users 로 테이블명 변경해줌
@Entity(name = "users")
public class User {

    @Id
    // 기본키 생성을 DB에 위임 ( AUTO_INCREMENT)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nullable = false : not null
    // unique = true : 유일성 조건 설정
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;



    // user 객체 생성시 필요한 정보 = username, password
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}