package com.project01.quiz.controller;


import com.project01.quiz.dto.request.QuestionRequest;
import com.project01.quiz.dto.response.ApiResponse;
import com.project01.quiz.dto.response.QuestionResponse;
import com.project01.quiz.services.QuestionServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private QuestionServices questionServices;


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ApiResponse<List<QuestionResponse>> getQuestionByTopicId(@RequestParam int topicId) {
        List<QuestionResponse> questionResponses=questionServices.getQuestionByTopicId(topicId);
        return ApiResponse.<List<QuestionResponse>>builder()
                .message("success")
                .data(questionResponses)
                .code(HttpStatus.OK)
                .build();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse addQuestion(@RequestBody @Valid QuestionRequest questionRequest) {

        questionServices.addQuestion(questionRequest);
        return ApiResponse.builder()
                .message("success")
                .code(HttpStatus.OK)
                .build();
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse updateQuestion(@RequestBody @Valid QuestionRequest questionRequest,@PathVariable Long id) {

        questionServices.updateQuestion(questionRequest,id);
        return ApiResponse.builder()
                .message("success")
                .code(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse deleteQuestion(@PathVariable List<Long> id) {

        questionServices.deleteQuestion(id);
        return ApiResponse.builder()
                .message("success")
                .code(HttpStatus.OK)
                .build();
    }
}
