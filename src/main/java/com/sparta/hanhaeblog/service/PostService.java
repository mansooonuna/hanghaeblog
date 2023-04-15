package com.sparta.hanhaeblog.service;

import com.sparta.hanhaeblog.dto.ModifiedResponseDto;
import com.sparta.hanhaeblog.dto.PostRequestDto;
import com.sparta.hanhaeblog.dto.PostResponseDto;
import com.sparta.hanhaeblog.entity.Post;
import com.sparta.hanhaeblog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public PostResponseDto getPost(Long id) {
        Post post = checkPost(id);
        return new PostResponseDto(post);
    }

    @Transactional
    public Post createPost(PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        postRepository.save(post);
        return post;
    }

    @Transactional
    public ModifiedResponseDto update(Long id, PostRequestDto requestDto) {
        Post post = checkPost(id);
        if(post.getPassword().equals(requestDto.getPassword())) {
            post.update(requestDto);
        }
        else {
            System.out.println("비밀번호가 일치하지 않습니다.");
        }
        return new ModifiedResponseDto(post);
    }

    public String deletePost(Long id, PostRequestDto requestDto) {
        Post post = checkPost(id);
        if(post.getPassword().equals(requestDto.getPassword())) {
            postRepository.delete(post);
            return "게시글을 삭제했습니다.";
        }
        else {
            return "비밀번호가 일치하지 않습니다.";
        }
    }

    public Post checkPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
    }
}
