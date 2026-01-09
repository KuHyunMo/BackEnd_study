package com.example.cnubackend.todo;


import lombok.RequiredArgsConstructor;
import com.example.cnubackend.todo.dto.TodoDto;
import com.example.cnubackend.todo.dto.TodoResponseDto;
import com.example.cnubackend.exception.NotFoundException;
import com.example.cnubackend.user.dto.UserDto;
import com.example.cnubackend.user.User;
import com.example.cnubackend.user.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
        
        private final TodoRepository todoRepository;
        private final UserRepository userRepository;

        private TodoResponseDto entityToDto(Todo todo) {
                String nickname = "알수없음";

                // 작성자 정보가 있고, 닉네임이 있다면 가져오기
                if (todo.getCreatedBy() != null) {
                        nickname = todo.getCreatedBy().getNickname();
                }

                return TodoResponseDto.builder()
                        .id(todo.getId())
                        .title(todo.getTitle())
                        .completed(todo.getCompleted())
                        .nickname(nickname)
                        .build();
        }

        public TodoResponseDto create(Long userId, TodoDto dto) {
                User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
                Todo todo = Todo.builder()
                        .title(dto.getTitle())
                        .completed(dto.getCompleted())
                        .createdBy(user)
                        .build();
                Todo savedTodo = todoRepository.save(todo);

                return entityToDto(savedTodo);
        }

        public List<TodoResponseDto> getAll() {
                return todoRepository.findAll().stream()
                        .map(this::entityToDto)
                        .toList();
        }

        public TodoResponseDto getById(Long id) {
                Todo todo = todoRepository.findById(id).orElseThrow(() -> new NotFoundException("Todo not found"));
                return entityToDto(todo);
        }

        public void deleteById(Long id)
        { todoRepository.deleteById(id); }

        public TodoResponseDto updateById(Long id, TodoDto dto) {
                Todo todo = todoRepository.findById(id).orElseThrow(() -> new NotFoundException("Todo not found"));
                todo = Todo.builder()
                        .id(todo.getId())
                        .title(dto.getTitle())
                        .completed(dto.getCompleted())
                        .createdBy(todo.getCreatedBy())
                        .build();

                Todo updatedTodo = todoRepository.save(todo);

                return entityToDto(updatedTodo);
        }

        public List<TodoResponseDto> getByCreatedById(Long createdBy) {
                return todoRepository.findByCreatedById(createdBy).stream()
                        .map(this::entityToDto)
                        .toList();
        }

        public List<TodoResponseDto> getCompletedTodos(Boolean completed) {
                return todoRepository.findByCompleted(completed).stream()
                        .map(this::entityToDto)
                        .toList();
        }

        public List<TodoResponseDto> searchByTitle(String keyword) {

                return todoRepository.findByTitleContaining(keyword).stream()
                        .map(this::entityToDto)
                        .toList();
        }

        public List<TodoResponseDto> getTodos(String keyword, Boolean completed) {

                if (keyword != null && !keyword.isEmpty() && completed != null) {
                        List<Todo> todoList = todoRepository.findByTitleContainingAndCompleted(keyword, completed);
                        return todoList.stream().map(this::entityToDto).toList();
                }
                else if (keyword != null && !keyword.isEmpty()) {
                        return searchByTitle(keyword);
                }
                else if (completed != null) {
                        return getCompletedTodos(completed);
                }
                else {
                        return getAll();
                }
        }
}
//검색 기능 고도화 중 위 함수 이용해서, html 수정하면 될 듯