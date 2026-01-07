package com.example.cnubackend.todo;


import lombok.RequiredArgsConstructor;
import com.example.cnubackend.todo.dto.TodoDto;
import com.example.cnubackend.todo.dto.TodoResponseDto;
import com.example.cnubackend.exception.NotFoundException;
import com.example.cnubackend.user.dto.UserDto;
import com.example.cnubackend.user.User;
import com.example.cnubackend.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
        
        private final TodoRepository todoRepository;
        private final UserRepository userRepository;

        public TodoDto create(Long userId, TodoDto dto) {
                User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
                Todo todo = Todo.builder()
                        .title(dto.getTitle())
                        .completed(dto.getCompleted())
                        .createdBy(user)
                        .build();
                Todo savedTodo = todoRepository.save(todo);

                return TodoDto.builder()
                        .id(savedTodo.getId())
                        .title(savedTodo.getTitle())
                        .completed(savedTodo.getCompleted())
                        .build();
        }

        // public List<TodoResponseDto> getAll() {
        //         return todoRepository.findAll().stream()
        //                 .map(todo -> TodoResponseDto.builder()
        //                         .id(todo.getId())
        //                         .title(todo.getTitle())
        //                         .completed(todo.getCompleted())
        //                         .createdBy(
        //                                 UserDto.builder()
        //                                         .id(todo.getCreatedBy().getId())
        //                                         .name(todo.getCreatedBy().getName())
        //                                         .username(todo.getCreatedBy().getUsername())
        //                                         .build()
        //                         )
        //                         .build())
        //                 .toList();
        // }

        ////////////
        public List<TodoResponseDto> getAll() {
                return todoRepository.findAll().stream()
                        .map(todo -> {
                        // 1. 작성자(User) 정보가 있는지 먼저 확인합니다.
                        UserDto userDto = null;
                        if (todo.getCreatedBy() != null) {
                                userDto = UserDto.builder()
                                        .id(todo.getCreatedBy().getId())
                                        .nickname(todo.getCreatedBy().getNickname())
                                        .username(todo.getCreatedBy().getUsername())
                                        .build();
                        }

                    // 2. 작성자가 없으면 null인 채로, 있으면 정보를 담아서 반환합니다.
                        return TodoResponseDto.builder()
                                .id(todo.getId())
                                .title(todo.getTitle())
                                .completed(todo.getCompleted())
                                .createdBy(userDto) // 안전하게 넣기
                                .build();
                        })
                        .toList();
        }

        ///////////////////////


        public TodoDto getById(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new NotFoundException("Todo not found"));
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
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new NotFoundException("Todo not found"));
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

    public List<TodoDto> searchByTitle(String keyword) {
        List<Todo> todos = todoRepository.findByTitleContaining(keyword);
        return todos.stream()
                .map(todo -> TodoDto.builder()
                        .id(todo.getId())
                        .title(todo.getTitle())
                        .completed(todo.getCompleted())
                        .build()
                ).toList();
    }

    public List<TodoDto> getCompletedTodos(Boolean completed) {
        List<Todo> todos = todoRepository.findByCompleted(completed);
        return todos.stream()
                .map(todo -> TodoDto.builder()
                        .id(todo.getId())
                        .title(todo.getTitle())
                        .completed(todo.getCompleted())
                        .build()
                ).toList();
    }

    public List<TodoDto> getByCreatedById(Long createdBy) {
        List<Todo> todos = todoRepository.findByCreatedById(createdBy);
        return todos.stream()
                .map(todo -> TodoDto.builder()
                        .id(todo.getId())
                        .title(todo.getTitle())
                        .completed(todo.getCompleted())
                        .build()
                ).toList();
    }
}