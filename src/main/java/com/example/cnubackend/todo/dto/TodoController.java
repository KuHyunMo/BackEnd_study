package com.example.cnubackend.todo.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("")
    public ResponseEntity<List<TodoDto>> getALl() {
        List<TodoDto> todos = todoService.getAll();

        return ResponseEntity.ok(todos);
    }

    @PostMapping("")
    public ResponseEntity<TodoDto> create(@RequestBody TodoDto dto) {
        TodoDto createdTodo = todoService.create(dto);
        return ResponseEntity.ok(createdTodo);
    }
}


//./gradlew bootRun 해야 서버 실행 node server.js 이거랑 비슷한 역할
