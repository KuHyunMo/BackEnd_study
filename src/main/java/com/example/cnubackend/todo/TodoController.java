package com.example.cnubackend.todo;


import com.example.cnubackend.todo.dto.TodoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;


    //post mapping
    @PostMapping("")
    public ResponseEntity<TodoDto> create(@RequestBody TodoDto dto) {
        TodoDto createdTodo = todoService.create(dto);
        return ResponseEntity.ok(createdTodo);
    }

    //get mapping

    @GetMapping("")
    public ResponseEntity<List<TodoDto>> getALl() {
        List<TodoDto> todos = todoService.getAll();

        return ResponseEntity.ok(todos);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TodoDto>> search(@RequestParam String keyword) {
        List<TodoDto> results = todoService.searchBytitle(keyword);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<TodoDto>> getCompletedTodos(@RequestParam Boolean completed) {
        List<TodoDto> completedTodos = todoService.getCompletedTodos(completed);
        return ResponseEntity.ok(completedTodos);
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
