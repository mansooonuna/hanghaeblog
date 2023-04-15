package com.sparta.hanhaeblog.dto;

import com.sparta.hanhaeblog.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    // 여기 선언된 순서대로 출력됨.
    private String title;
    private String author;
    private String contents;
    private LocalDateTime createdAt;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.author = post.getAuthor();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
    }
}
