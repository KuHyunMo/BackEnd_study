package com.example.cnubackend.auth.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class SignupResponseDto {
    private Long id;
    private String nickname;
}
