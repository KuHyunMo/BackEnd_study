package com.example.cnubackend.user;

import lombok.RequiredArgsConstructor;

//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
//import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    //서버 작동 획인용 디버깅 코드
    @GetMapping("")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello User!");
    }

    //로그인한 사용자의 아이디를 반환<인증 테스트용>
    @PostMapping("/authenticated") //Spring Security가 인증된 사용자 정보를 SecurityContext에서 꺼내서 userDetails에 자동로 주입
    public ResponseEntity<String> authenticatedWithUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok("Hello " + (userDetails != null ? userDetails.getUsername() : "anonymous"));
    }

}
