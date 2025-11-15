package com.example.cnubackend.user;

import org.springframework.stereotype.Service;
import com.example.cnubackend.user.dto.UserDto;
import com.example.cnubackend.user.dto.UserSignupDto;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public UserDto create(UserSignupDto dto) {
        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .pasward(dto.getPasward())
                .build();
        User savedUser = userRepository.save(user);

        return UserDto.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .build();
    }

}
