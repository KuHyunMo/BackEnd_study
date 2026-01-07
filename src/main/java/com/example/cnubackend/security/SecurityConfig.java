package com.example.cnubackend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors
                        .configurationSource(request -> {
                            var corsConfig = new CorsConfiguration();
                            corsConfig.setAllowCredentials(true);
                            corsConfig.setAllowedOriginPatterns(List.of("*")); // 모든 Origin 허용 (개발용)
                            //corsConfig.setAllowedOrigins(List.of("http://localhost:3000")); //http://localhost:3000에서 오는 요청 허용
                            corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE","PATCH")); //모든 HTTP 메서드 허용
                            corsConfig.setAllowedHeaders(List.of("*")); //모든 헤더 허용
                            return corsConfig;
                        }))
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/auth/signup", "/api/auth/login").permitAll() // 회원가입 및 로그인은 모두 허용
                        .anyRequest().authenticated() // 그 외의 요청은 인증 필요
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // JWT 인증 필터 추가
        return http.build();
    }

}