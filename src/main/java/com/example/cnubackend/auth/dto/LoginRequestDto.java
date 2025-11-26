package com.example.cnubackend.auth.dto;

import lombok.*;

@AllArgsConstructor //클래스에 존재하는 모든 필드를 파라미터로 받는 생성자를 생성
@NoArgsConstructor //기본 생성자 생선
@Builder
@Setter
@Getter

public class LoginRequestDto {
    private String username;
    private String password;
}
