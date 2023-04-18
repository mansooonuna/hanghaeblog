package com.sparta.hanhaeblog.dto;

import com.sparta.hanhaeblog.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ModifiedResponseDto {
    private String title;
    private String username;
    private String contents;
    private LocalDateTime modifiedAt;

    public ModifiedResponseDto(Post post) {
        this.title = post.getTitle();
        this.username = post.getUsername();
        this.contents = post.getContents();
        this.modifiedAt = post.getModifiedAt();
    }
}
