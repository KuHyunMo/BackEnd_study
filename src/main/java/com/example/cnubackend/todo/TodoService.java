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
//관리자용 통합 검색 기능
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
//사용자용 id별 검색
        public List<TodoResponseDto> getTodos(Long userId, String keyword, Boolean completed) {

                List<Todo> todoList;
                if (keyword != null && !keyword.isEmpty() && completed != null) {
                        todoList = todoRepository.findByCreatedByIdAndTitleContainingAndCompleted(userId, keyword, completed);
                }
                else if (keyword != null && !keyword.isEmpty()) {
                        todoList = todoRepository.findByCreatedByIdAndTitleContaining(userId, keyword);
                }
                else if (completed != null) {
                        todoList = todoRepository.findByCreatedByIdAndCompleted(userId, completed);
                }
                else {
                        todoList = todoRepository.findByCreatedById(userId);
                }
                return todoList.stream().map(this::entityToDto).toList();
        }
}
//검색 기능 고도화 중 위 함수 이용해서, html 수정하면 될 듯