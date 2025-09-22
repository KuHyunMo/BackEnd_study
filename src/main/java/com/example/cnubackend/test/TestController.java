package com.example.cnubackend.test;

import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/posts")
    public ResponseEntity<TestDto> test(@PathParam("name") String name) {
        TestDto dto = new TestDto();
        dto.setName(name);
        dto.setAge(30);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/test")
    public ResponseEntity<TestDto> testPost(@RequestBody TestDto dto) {
        dto.setAge(dto.getAge() + 1);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/test/{id}")
    public ResponseEntity<Integer> testDelete(@PathVariable Integer id) {

        return ResponseEntity.ok(id);
    }
}