package com.example.cnubackend.todo;


import lombok.RequiredArgsConstructor;
import com.example.cnubackend.todo.dto.TodoDto;
import com.example.cnubackend.exception.NotFoundException;
import com.example.cnubackend.user.User;
import com.example.cnubackend.user.UserRepository;
import org.springframework.stereotype.Service;



import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
        
        private final TodoRepository todoRepository;
        private final UserRepository userRepository;

        public TodoDto create(TodoDto dto) {
                User user = userRepository.findById(dto.getCreatedBy()).orElseThrow(() -> new NotFoundException("User not found"));
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

        //////////////////////////////////////////////////
    
        public List<TodoDto> searchBytitle(String keyward) {
                List<Todo> todos = todoRepository.findByTitleContaining(keyward);
                return todos.stream()
                        .map(todo -> TodoDto.builder()
                                .id(todo.getId())
                                .title(todo.getTitle())
                                .completed(todo.getCompleted())
                                .build())
                        .toList();
        }

        public List<TodoDto> getCompletedTodos(boolean completed) {
                List<Todo> completedTodos = todoRepository.findByCompleted(completed);
                        
                return completedTodos.stream()    
                        .map(todo -> TodoDto.builder()
                                .id(todo.getId())
                                .title(todo.getTitle())
                                .completed(todo.getCompleted())
                                .build())
                        .toList();
        }
        
        public List<TodoDto> getByCreatedById(Long createdBy) {
                List<Todo> todos = todoRepository.findByCreatedById(createdBy);
                return todos.stream()
                        .map(todo -> TodoDto.builder()
                                .id(todo.getId())
                                .title(todo.getTitle())
                                .completed(todo.getCompleted())
                                .build())
                        .toList();
        }
}

