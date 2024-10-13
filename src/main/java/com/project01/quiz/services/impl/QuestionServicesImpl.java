package com.project01.quiz.services.impl;

import com.project01.quiz.dto.request.QuestionRequest;
import com.project01.quiz.dto.response.QuestionResponse;
import com.project01.quiz.entity.QuestionEntity;
import com.project01.quiz.entity.TopicEntity;
import com.project01.quiz.repository.QuestionRepository;
import com.project01.quiz.repository.TopicRepository;
import com.project01.quiz.services.QuestionServices;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class QuestionServicesImpl implements QuestionServices {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void addQuestion(QuestionRequest questionRequest) {
        QuestionEntity questionEntity = modelMapper.map(questionRequest, QuestionEntity.class);
        TopicEntity topicEntity =topicRepository.findById(questionRequest.getTopicId()).get();
        questionEntity.setTopic(topicEntity);
        questionRepository.save(questionEntity);
    }

    @Override
    public void updateQuestion(QuestionRequest questionRequest,Long id) {
        QuestionEntity questionEntity = questionRepository.findById(id).get();
        questionEntity = modelMapper.map(questionRequest, QuestionEntity.class);
        TopicEntity topicEntity =topicRepository.findById(questionRequest.getTopicId()).get();
        questionEntity.setTopic(topicEntity);
        questionRepository.save(questionEntity);
    }

    @Override
    public List<QuestionResponse> getQuestionByTopicId(int topicId) {
        List<QuestionEntity> questionEntityList=questionRepository.findByTopic_Id(topicId);
        List<QuestionResponse> questionResponses=new ArrayList<>();
        for(QuestionEntity questionEntity:questionEntityList){
            QuestionResponse questionResponse=modelMapper.map(questionEntity, QuestionResponse.class);
            List<String> answers= Arrays.stream(questionEntity.getAnswer().split("/")).toList();
            questionResponse.setAnswerQuestion(answers);
            questionResponses.add(questionResponse);
        }
        return questionResponses;
    }

    @Override
    public void deleteQuestion(List<Long> questionIds) {
        questionRepository.deleteByIdIn(questionIds);
    }
}
