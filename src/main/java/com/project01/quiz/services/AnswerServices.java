package com.project01.quiz.services;

import com.project01.quiz.dto.request.AnswerRequest;
import com.project01.quiz.dto.response.AnswerResponse;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface AnswerServices {
     void createAnswer(AnswerRequest answerRequest);
     void deleteAnswer(Long topicId);
     AnswerRequest getAnswerByUserIdAndTopicId(Long userId, Long topicId);
     List<AnswerResponse> historyAnswer(Long userId);
}
