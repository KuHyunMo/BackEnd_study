package com.example.cnubackend.user;

import org.springframework.stereotype.Service;
import com.example.cnubackend.user.dto.UserDto;
import com.example.cnubackend.user.dto.UserSignupDto;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public UserDto create(UserSignupDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
        User savedUser = userRepository.save(user);

        return UserDto.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .build();
    }


    public List<UserDto> getAll() {
                return userRepository.findAll().stream()
                        .map(user -> UserDto.builder()
                                .id(user.getId())
                                .username(user.getUsername())
                                .email(user.getEmail())
                                .password(user.getPassword())
                                .build())
                        .toList();
        }

}
