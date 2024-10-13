package com.project01.quiz.dto.response;


import com.project01.quiz.dto.request.FieldDTO;
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
public class AnswerResponse {
     private Long id;
     private Long topicId;
     private Long userId;
     private List<FieldDTO> answers=new ArrayList<>();
}
