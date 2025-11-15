package com.example.cnubackend.todo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByTitleContaining(String keyword);
    List<Todo> findByCompleted(Boolean completed);
    List<Todo> findByCreatedById(Long createdBy);
}
