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
    public ResponseEntity<TodoResponseDto> create(@RequestBody TodoDto dto, @AuthenticationPrincipal UserDetails user) {
        Long userId = Long.valueOf(user.getUsername());
        TodoResponseDto createdTodo = todoService.create(userId, dto);
        return ResponseEntity.ok(createdTodo);
    }

    //get mapping
    @GetMapping("")
    public ResponseEntity<List<TodoResponseDto>> getALl() {
        List<TodoResponseDto> todos = todoService.getAll();

        return ResponseEntity.ok(todos);
    }

    @GetMapping("/my")
    public ResponseEntity<List<TodoResponseDto>> getMyTodos(@AuthenticationPrincipal UserDetails user) {
        Long userId = Long.valueOf(user.getUsername());
        List<TodoResponseDto> myTodos = todoService.getByCreatedById(userId);
        return ResponseEntity.ok(myTodos);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TodoResponseDto>> getTodos(@AuthenticationPrincipal UserDetails user, @RequestParam(required = false) String keyword,@RequestParam(required = false) Boolean completed) {
    // TodoService에 새로 만든 통합 조회 메서드 호출
    // keyword와 completed가 null인지 아닌지에 따라 Service에서 4가지 상황을 처리함
        Long userId = Long.valueOf(user.getUsername());
        List<TodoResponseDto> todos = todoService.getTodos(userId, keyword, completed); 
        return ResponseEntity.ok(todos);
    }

    // @GetMapping("/search")
    // public ResponseEntity<List<TodoResponseDto>> search(@RequestParam String keyword) {
    //     List<TodoResponseDto> results = todoService.searchByTitle(keyword);
    //     return ResponseEntity.ok(results);
    // }

    @GetMapping("/completed")
    public ResponseEntity<List<TodoResponseDto>> getCompletedTodos(@RequestParam Boolean completed) {
        List<TodoResponseDto> completedTodos = todoService.getCompletedTodos(completed);
        return ResponseEntity.ok(completedTodos);
    }

    

    
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> getById(@PathVariable Long id) {
        TodoResponseDto todo = todoService.getById(id);
        return ResponseEntity.ok(todo);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TodoResponseDto>> getByUserId(@PathVariable Long userId) {
        List<TodoResponseDto> UserIdtodos = todoService.getByCreatedById(userId);
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
    public ResponseEntity<TodoResponseDto> updateById(@PathVariable Long id, @RequestBody TodoDto dto) {
        TodoResponseDto updatedTodo = todoService.updateById(id, dto);
        return ResponseEntity.ok(updatedTodo);
    }    
    
}


//./gradlew bootRun 해야 서버 실행 node server.js 이거랑 비슷한 역할
