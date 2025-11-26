package com.example.cnubackend.auth;

import com.example.cnubackend.auth.dto.*;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //API 컨트롤러야. 결과는 JSON으로 반환
@RequiredArgsConstructor //final 붙은 애들 생성자 자동으로 생성
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup") //가입
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
        SignupResponseDto dto = authService.signup(signupRequestDto);
        return ResponseEntity.ok(dto);
    }
    //프론트엔드에서 보낸 JSON 데이터를 자바 객체(SignupRequestDto)로 변환해서 받음
    //처리 결과를 포함하여 HTTP 상태 코드 200(OK)를 응답으로 보냄
    
    @PostMapping("login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto dto = authService.login(loginRequestDto);
        return ResponseEntity.ok(dto);
    }
}
