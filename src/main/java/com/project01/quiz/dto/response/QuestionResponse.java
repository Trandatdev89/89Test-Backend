package com.project01.quiz.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class QuestionResponse {
    private Long id;
    private String question;
    private List<String> answerQuestion=new ArrayList<>();
    private Long correctAnswer;
    private Long topicId;
}
