package com.example.miniblog.service;

import com.example.miniblog.domain.User;
import com.example.miniblog.dto.LoginRequest;
import com.example.miniblog.dto.SignupRequest;
import com.example.miniblog.jwt.JwtUtil;
import com.example.miniblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void signup(SignupRequest request) {
        String email = request.getEmail();
        String rawPassword = request.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        User user = new User(email, encodedPassword);
        userRepository.save(user);
    }

    public String login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                                                                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

                                                                if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
                                                                    throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
                                                                }

                                                                return  jwtUtil.createToken(user.getEmail());

    }
    
}
