package com.project01.quiz.services;

import com.project01.quiz.dto.request.QuestionRequest;
import com.project01.quiz.dto.response.QuestionResponse;

import java.util.List;

public interface QuestionServices {
    void addQuestion(QuestionRequest questionRequest);
    void deleteQuestion(List<Long> questionId);
    void updateQuestion(QuestionRequest questionRequest,Long questionId);
    List<QuestionResponse> getQuestionByTopicId(int topicId);
}
