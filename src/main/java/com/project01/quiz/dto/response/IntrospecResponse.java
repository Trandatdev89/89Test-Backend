package com.project01.quiz.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IntrospecResponse {
    private boolean valid;
}
