package com.project01.quiz.services;

import com.project01.quiz.dto.request.TopicRequest;
import com.project01.quiz.dto.response.TopicResponse;

import java.util.List;

public interface TopicServices {
    List<TopicResponse> getAllTopics();
    TopicResponse getTopicById(Long id);
    TopicResponse createTopic(TopicRequest topicRequest);
    void deleteTopic(List<Long> id);
}
