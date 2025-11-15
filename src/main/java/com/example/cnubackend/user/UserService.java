package com.example.cnubackend.user;

import org.springframework.stereotype.Service;
import com.example.cnubackend.user.dto.UserDto;
import com.example.cnubackend.user.dto.UserSignupDto;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public UserDto create(UserSignupDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .passward(userDto.getPassword())
                .build();
        User savedUser = userRepository.save(user);

        return UserDto.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .build();
    }

}
