package com.example.blog.entity;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // 엔티티에 @Version이 있으면 굳이 사용 안해도 됨
}
