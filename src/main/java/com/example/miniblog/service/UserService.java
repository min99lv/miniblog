package com.example.miniblog.service;

import com.example.miniblog.domain.User;
import com.example.miniblog.dto.SignupRequest;
import com.example.miniblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void signup(SignupRequest request) {
        String email = request.getEmail();
        String rawPassword = request.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        User user = new User(email, encodedPassword);
        userRepository.save(user);
    }
    
}
