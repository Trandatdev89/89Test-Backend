package com.project01.quiz.controller;


import com.project01.quiz.dto.request.TopicRequest;
import com.project01.quiz.dto.response.ApiResponse;
import com.project01.quiz.dto.response.AuthResponse;
import com.project01.quiz.dto.response.TopicResponse;
import com.project01.quiz.repository.TopicRepository;
import com.project01.quiz.services.TopicServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topic")
public class TopicController {

    @Autowired
    private TopicServices topicServices;

    @GetMapping
//    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ApiResponse<List<TopicResponse>> getAllTopics() {
        List<TopicResponse> topicResponseList=topicServices.getAllTopics();
        return ApiResponse.<List<TopicResponse>>builder()
                .code(HttpStatus.OK)
                .message("Success !")
                .data(topicResponseList)
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ApiResponse<TopicResponse> getTopicById(@PathVariable Long id) {
        TopicResponse topicResponseList=topicServices.getTopicById(id);
        return ApiResponse.<TopicResponse>builder()
                .code(HttpStatus.OK)
                .message("Success !")
                .data(topicResponseList)
                .build();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse<TopicResponse> createTopic(@ModelAttribute TopicRequest topicRequest) {
        TopicResponse topicResponse1= topicServices.createTopic(topicRequest);
        return ApiResponse.<TopicResponse>builder()
                .code(HttpStatus.OK)
                .data(topicResponse1)
                .message("Success !")
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse deleteTopic(@PathVariable List<Long> id) {
        topicServices.deleteTopic(id);
        return ApiResponse.builder()
                .code(HttpStatus.OK)
                .message("Success !")
                .build();
    }
}
