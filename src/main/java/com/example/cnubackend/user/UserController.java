package com.example.cnubackend.user;

import lombok.RequiredArgsConstructor;
import com.example.cnubackend.user.dto.UserDto;
import com.example.cnubackend.user.dto.UserSignupDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello User!");
    }

    @PostMapping("")
    public ResponseEntity<UserDto> create(@RequestBody UserSignupDto userDto) {
        UserDto createdUser = userService.create(userDto);
        return ResponseEntity.ok(createdUser);
    }

}
