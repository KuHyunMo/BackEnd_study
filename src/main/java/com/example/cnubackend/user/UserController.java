package com.example.cnubackend.user;

import lombok.RequiredArgsConstructor;
import com.example.cnubackend.user.dto.UserDto;
import com.example.cnubackend.user.dto.UserSignupDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello User!");
    }

    @GetMapping("all")
    public ResponseEntity<List<UserDto>> getALl() {
        List<UserDto> todos = userService.getAll();

        return ResponseEntity.ok(todos);
    }

    @PostMapping("")
    public ResponseEntity<UserDto> create(@RequestBody UserSignupDto userDto) {
        UserDto createdUser = userService.create(userDto);
        return ResponseEntity.ok(createdUser);
    }

}
