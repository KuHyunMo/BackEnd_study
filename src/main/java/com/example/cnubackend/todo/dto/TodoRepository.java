package com.example.cnubackend.todo.dto;

import com.example.cnubackend.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    
}
