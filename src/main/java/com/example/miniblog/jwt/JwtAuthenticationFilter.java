package com.example.miniblog.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{

        // 1. Authorization 헤더에서 토큰 꺼내기
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);  // 토큰 없으면 그냥 통과
            return;
        }

        String token = authHeader.substring(7); //  "Bearer 제거"

        // 2. 토큰 유효성 검사
        if (!jwtUtil.validateToken(token)){
            filterChain.doFilter(request, response);
            return;
        }

        // 3. 토큰에서 이메일 꺼내기
        String email = jwtUtil.getEmailFromToken(token);

        // 4. 스프링 시큐리티 인증 객체 만들기
        UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(email,null,null);

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


        // 5. 시큐리티 컨텍스트에 인증 객체 등록
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 다음 필터로 넘기기
        filterChain.doFilter(request, response);
    }

    
}
