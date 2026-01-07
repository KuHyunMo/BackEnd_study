package com.example.cnubackend.todo.dto;

import com.example.cnubackend.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TodoResponseDto {
    Long id;
    String title;
    Boolean completed;
    UserDto createdBy;
    //private String name;
    //private String username;
}
