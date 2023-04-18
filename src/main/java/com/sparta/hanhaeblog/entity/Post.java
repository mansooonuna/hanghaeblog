package com.sparta.hanhaeblog.entity;

import com.sparta.hanhaeblog.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String contents;


    public Post(String title, String userName, String contents) {
        this.title = title;
        this.userName = userName;
        this.contents = contents;
    }

    public Post(PostRequestDto requestDto, Long id) {
        this.title = requestDto.getTitle();
        this.userName = requestDto.getAuthor();             // TODO : user 엔티티에서 동시에 변경 요청
        this.contents = requestDto.getContents();
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.userName = requestDto.getAuthor();             // TODO : user 엔티티에서 동시에 변경 요청
        this.contents = requestDto.getContents();
    }
}
