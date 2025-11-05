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

    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getById(@PathVariable Long id) {
        TodoDto todo = todoService.getById(id);
        return ResponseEntity.ok(todo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        todoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateById(@PathVariable Long id, @RequestBody TodoDto dto) {
        TodoDto updatedTodo = todoService.updateById(id, dto);
        return ResponseEntity.ok(updatedTodo);
    }
    
}


//./gradlew bootRun 해야 서버 실행 node server.js 이거랑 비슷한 역할
