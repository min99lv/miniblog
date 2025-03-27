package com.example.miniblog.controller;

import com.example.miniblog.dto.PostRequest;
import com.example.miniblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostRequest request, Authentication authentication) {
        String email = (String) authentication.getPrincipal(); // 토큰에서 꺼낸 email
        postService.createPost(request, email);
        return ResponseEntity.ok("게시글 등록 완료!");
    
}
}
