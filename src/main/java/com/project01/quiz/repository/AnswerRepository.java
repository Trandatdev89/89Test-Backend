package com.project01.quiz.repository;

import com.project01.quiz.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
    void deleteByTopic_id(Long topicId);
    List<AnswerEntity> findByTopic_idAndUser_Id(Long topicId,Long userId);
    boolean existsByTopic_idAndUser_Id(Long topicId,Long userId);
    void deleteAllByTopic_idAndUser_Id(Long topicId,Long userId);
    List<AnswerEntity> findByUser_Id(Long userId);
}
