package com.sparta.hanhaeblog;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.hanhaeblog.dto.PostRequestDto;
import com.sparta.hanhaeblog.entity.Post;
import com.sparta.hanhaeblog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EnableJpaAuditing
@SpringBootApplication
public class HanhaeblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(HanhaeblogApplication.class, args);
	}

	@Autowired
	private PostRepository postRepository;

	@Bean
	public ApplicationRunner applicationRunner() {
		return args -> {
			File json = ResourceUtils.getFile("classpath:PostData.json");

			List<PostRequestDto> list = new ObjectMapper().readValue(json, new TypeReference<>(){});
			List<Post> courses = list.stream().map(Post::new).collect(Collectors.toCollection(ArrayList::new));

			postRepository.saveAll(courses);
		};
	}
}
