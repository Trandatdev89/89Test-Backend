package com.project01.quiz.repository;

import com.project01.quiz.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<TopicEntity, Long> {
    void deleteByIdIn(List<Long> id);

}
