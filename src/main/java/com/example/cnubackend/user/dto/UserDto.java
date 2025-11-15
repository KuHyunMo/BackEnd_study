package com.example.cnubackend.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserDto {
    protected Long id;
    protected String username;
    protected String email;
    protected String pasward;
}
