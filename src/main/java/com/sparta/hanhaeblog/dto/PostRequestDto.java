package com.sparta.hanhaeblog.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String author;
    private String contents;
    private Long password;
}
