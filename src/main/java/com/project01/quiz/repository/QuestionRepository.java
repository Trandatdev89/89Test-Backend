package com.project01.quiz.repository;

import com.project01.quiz.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    void deleteByIdIn(List<Long> ids);
    List<QuestionEntity> findByTopic_Id(int TopicId);
    void deleteByTopic_IdIn(List<Long> id);
}
