package com.example.cnubackend.auth;


import com.example.cnubackend.auth.dto.*;
import com.example.cnubackend.security.JwtTokenProvider;
import com.example.cnubackend.user.User;
import com.example.cnubackend.user.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public SignupResponseDto signup(SignupRequestDto signupRequestDto) { //회원가입
        
        if(userRepository.existsByUsername(signupRequestDto.getUsername()) ) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }
        
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword()); //비밀번호 암호화
        
        User user = User.builder()
                .nickname(signupRequestDto.getNickname())
                .username(signupRequestDto.getUsername())
                .password(encodedPassword) 
                .build();

        User savedUser = userRepository.save(user); //DB에 유저 저장

        return SignupResponseDto.builder()
                .id(savedUser.getId())
                .nickname(savedUser.getNickname())
                .build();
        }


        public LoginResponseDto login(LoginRequestDto loginRequestDto) {
            //user 찾기
            User user = userRepository.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));
            //비밀번호 확인
            if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid username or password");
            }

            //검사 완료 시 토큰 생성 및 반환
            String token = jwtTokenProvider.generateToken(user.getId());
            return LoginResponseDto.builder()
                .token(token)
                .build();
        }
}
