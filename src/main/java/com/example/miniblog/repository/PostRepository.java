package com.example.miniblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.miniblog.domain.Post;

public interface PostRepository  extends JpaRepository<Post, Long>{

}
