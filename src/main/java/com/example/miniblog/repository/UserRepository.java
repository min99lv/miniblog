package com.example.miniblog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.miniblog.domain.User;

public interface UserRepository extends JpaRepository<User, Long> { 
     Optional<User> findByEmail(String email);


    
} 
