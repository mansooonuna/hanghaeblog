package com.sparta.hanhaeblog.repository;

import com.sparta.hanhaeblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);     //TODO : Optional 이 뭔지 잘 모르겠음
}