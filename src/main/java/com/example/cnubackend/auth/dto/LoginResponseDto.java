package com.example.cnubackend.auth.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class LoginResponseDto {
    private String token;
}
