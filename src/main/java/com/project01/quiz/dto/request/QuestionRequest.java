package com.project01.quiz.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class QuestionRequest {
    private Long id;
    private String question;
    private String answer;
    private Long correctAnswer;
    private Long topicId;
}
