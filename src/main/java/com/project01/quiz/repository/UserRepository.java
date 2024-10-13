package com.project01.quiz.repository;

import com.project01.quiz.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);
    Optional<UserEntity> findByUsername(String username);
    void deleteByIdIn(List<Long> ids);
    List<UserEntity> findByRoles_Code(String role);
}
