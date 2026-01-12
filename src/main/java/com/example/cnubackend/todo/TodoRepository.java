package com.example.cnubackend.todo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    //관리자용
    List<Todo> findByTitleContaining(String keyword);
    List<Todo> findByCompleted(Boolean completed);
    //List<Todo> findByCreatedById(Long createdBy);
    List<Todo> findByTitleContainingAndCompleted(String keyword, Boolean completed);


    // 1. 조건 없이 내 것 전체 조회
    List<Todo> findByCreatedById(Long userId);
    // 2. 내 것 중에서 제목 검색
    List<Todo> findByCreatedByIdAndTitleContaining(Long userId, String title);
    // 3. 내 것 중에서 완료 여부 필터링
    List<Todo> findByCreatedByIdAndCompleted(Long userId, Boolean completed);
    // 4. 내 것 중에서 제목 + 완료 여부 둘 다 필터링
    List<Todo> findByCreatedByIdAndTitleContainingAndCompleted(Long userId, String title, Boolean completed);
}
