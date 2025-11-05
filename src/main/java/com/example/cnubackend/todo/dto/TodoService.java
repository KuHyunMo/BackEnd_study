package com.example.cnubackend.todo.dto;

import com.example.cnubackend.todo.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
        
        private final TodoRepository todoRepository;

        public TodoDto create(TodoDto dto) {
                Todo todo = Todo.builder()
                        .title(dto.getTitle())
                        .completed(dto.getCompleted())
                        .build();
                Todo savedTodo = todoRepository.save(todo);

                return TodoDto.builder()
                        .id(savedTodo.getId())
                        .title(savedTodo.getTitle())
                        .completed(savedTodo.getCompleted())
                        .build();
        }

        public List<TodoDto> getAll() {
                return todoRepository.findAll().stream()
                        .map(todo -> TodoDto.builder()
                                .id(todo.getId())
                                .title(todo.getTitle())
                                .completed(todo.getCompleted())
                                .build())
                        .toList();
        }

/////////////////////////////////////////////////////

        public TodoDto getById(long id) {
                Todo todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
                
                return TodoDto.builder()
                        .id(todo.getId())
                        .title(todo.getTitle())
                        .completed(todo.getCompleted())
                        .build();
        }

        public void deleteById(Long id) {
                todoRepository.deleteById(id);
        }

        public TodoDto updateById(Long id, TodoDto dto) {
                Todo todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
                todo = Todo.builder()
                        .id(todo.getId())
                        .title(dto.getTitle())
                        .completed(dto.getCompleted())
                        .build();

                Todo updatedTodo = todoRepository.save(todo);

                return TodoDto.builder()
                        .id(updatedTodo.getId())
                        .title(updatedTodo.getTitle())
                        .completed(updatedTodo.getCompleted())
                        .build();
        }
}
