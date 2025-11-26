package com.example.cnubackend.auth.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class SignupRequestDto {
    private String username;
    private String password;
    private String name;
}
