package com.example.miniblog.service;

import org.springframework.stereotype.Service;

import com.example.miniblog.domain.Post;
import com.example.miniblog.dto.PostRequest;
import com.example.miniblog.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void createPost(PostRequest request, String email){
            Post post = Post.builder()
                                                .title(request.getTitle())
                                                .content(request.getContent())
                                                .authorEmail(email)
                                                .build();

            postRepository.save(post);

    }
    
}
