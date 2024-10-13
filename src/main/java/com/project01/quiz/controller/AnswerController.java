package com.project01.quiz.controller;


import com.project01.quiz.dto.request.AnswerRequest;
import com.project01.quiz.dto.response.AnswerResponse;
import com.project01.quiz.dto.response.ApiResponse;
import com.project01.quiz.dto.response.QuestionResponse;
import com.project01.quiz.repository.AnswerRepository;
import com.project01.quiz.services.AnswerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answer-user")
public class AnswerController {

    @Autowired
    private AnswerServices answerServices;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public AnswerRequest getAnswerByUserIdAndTopicId(@RequestParam Long userId, @RequestParam Long topicId) {
        return answerServices.getAnswerByUserIdAndTopicId(userId,topicId);
    }


    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ApiResponse createAnswer(@RequestBody AnswerRequest answerRequest) {

        answerServices.createAnswer(answerRequest);
        return ApiResponse.builder()
                .message("success")
                .code(HttpStatus.OK)
                .build();
    }


    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ApiResponse deleteAnswer(@RequestParam Long topicId) {
        answerServices.deleteAnswer(topicId);
        return ApiResponse.builder()
                .message("success")
                .code(HttpStatus.OK)
                .build();
    }

    @GetMapping("/history")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ApiResponse<List<AnswerResponse>> historyAnswer(@RequestParam Long userId) {
        List<AnswerResponse> answerResponseList= answerServices.historyAnswer(userId);
        return ApiResponse.<List<AnswerResponse>>builder()
                .message("success")
                .data(answerResponseList)
                .code(HttpStatus.OK)
                .build();
    }


}
