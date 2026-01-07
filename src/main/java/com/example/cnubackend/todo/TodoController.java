package com.example.cnubackend.todo;


import com.example.cnubackend.todo.dto.TodoDto;
import com.example.cnubackend.todo.dto.TodoResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    //post mapping
    @PostMapping("")
    public ResponseEntity<TodoDto> create(@RequestBody TodoDto dto, @AuthenticationPrincipal UserDetails user) {
        Long userId = Long.valueOf(user.getUsername());
        TodoDto createdTodo = todoService.create(userId, dto);
        return ResponseEntity.ok(createdTodo);
    }

    //get mapping
    @GetMapping("")
    public ResponseEntity<List<TodoResponseDto>> getALl() {
        List<TodoResponseDto> todos = todoService.getAll();

        return ResponseEntity.ok(todos);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TodoDto>> search(@RequestParam String keyword) {
        List<TodoDto> results = todoService.searchByTitle(keyword);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<TodoDto>> getCompletedTodos(@RequestParam Boolean completed) {
        List<TodoDto> completedTodos = todoService.getCompletedTodos(completed);
        return ResponseEntity.ok(completedTodos);
    }

    @GetMapping("/my")
    public ResponseEntity<List<TodoDto>> getMyTodos(@AuthenticationPrincipal UserDetails user) {
        Long userId = Long.valueOf(user.getUsername());
        List<TodoDto> myTodos = todoService.getByCreatedById(userId);
        return ResponseEntity.ok(myTodos);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getById(@PathVariable Long id) {
        TodoDto todo = todoService.getById(id);
        return ResponseEntity.ok(todo);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TodoDto>> getByUserId(@PathVariable Long userId) {
        List<TodoDto> UserIdtodos = todoService.getByCreatedById(userId);
        return ResponseEntity.ok(UserIdtodos);
    }
    
    //delete mapping
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        todoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //put mapping

    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateById(@PathVariable Long id, @RequestBody TodoDto dto) {
        TodoDto updatedTodo = todoService.updateById(id, dto);
        return ResponseEntity.ok(updatedTodo);
    }    
    
}


//./gradlew bootRun 해야 서버 실행 node server.js 이거랑 비슷한 역할
