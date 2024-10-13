package com.project01.quiz.services.impl;

import com.project01.quiz.dto.request.AnswerRequest;
import com.project01.quiz.dto.request.FieldDTO;
import com.project01.quiz.dto.response.AnswerResponse;
import com.project01.quiz.entity.AnswerEntity;
import com.project01.quiz.entity.TopicEntity;
import com.project01.quiz.entity.UserEntity;
import com.project01.quiz.repository.AnswerRepository;
import com.project01.quiz.repository.QuestionRepository;
import com.project01.quiz.repository.TopicRepository;
import com.project01.quiz.repository.UserRepository;
import com.project01.quiz.services.AnswerServices;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnswerServicesImpl implements AnswerServices {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;




    @Override
    public void createAnswer(AnswerRequest answerRequest) {
        if(answerRepository.existsByTopic_idAndUser_Id(answerRequest.getTopicId(),answerRequest.getUserId())){
            answerRepository.deleteAllByTopic_idAndUser_Id(answerRequest.getTopicId(),answerRequest.getUserId());
        }
        UserEntity user = userRepository.findById(answerRequest.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        TopicEntity topic = topicRepository.findById(answerRequest.getTopicId()).orElseThrow(() -> new RuntimeException("Topic not found"));

         for(FieldDTO item : answerRequest.getAnswers()){
             AnswerEntity answerEntity=new AnswerEntity();
             answerEntity.setTopic(topic);
             answerEntity.setUser(user);
             answerEntity.setAnsweruser(item.getAnswer());
             answerEntity.setQuestion(questionRepository.findById(item.getQuestionId()).orElseThrow(() -> new RuntimeException("Question not found")));
             answerRepository.save(answerEntity);
         }

    }

    @Override
    public void deleteAnswer(Long topicId) {
        answerRepository.deleteByTopic_id(topicId);
    }

    @Override
    public AnswerRequest getAnswerByUserIdAndTopicId(Long userId, Long topicId) {
        AnswerRequest answerRequests = new AnswerRequest();
        TopicEntity topicEntity = topicRepository.findById(topicId).orElseThrow(() -> new RuntimeException("Topic not found"));
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        answerRequests.setTopicId(topicEntity.getId());
        answerRequests.setUserId(userEntity.getId());

        List<FieldDTO> fieldDTOS = new ArrayList<>();
        List<AnswerEntity> answerEntities=answerRepository.findByTopic_idAndUser_Id(topicId,userId);
        for(AnswerEntity item : answerEntities){
            FieldDTO fieldDTO=new FieldDTO();
            fieldDTO.setAnswer(item.getAnsweruser());
            fieldDTO.setQuestionId(item.getQuestion().getId());
            fieldDTOS.add(fieldDTO);
        }
        answerRequests.setAnswers(fieldDTOS);
        return answerRequests;
    }

    @Override
    public List<AnswerResponse> historyAnswer(Long userId) {
        List<AnswerEntity> answerResponses = answerRepository.findByUser_Id(userId);
        Set<Long> topicId= answerResponses.stream().map(item->item.getTopic().getId()).collect(Collectors.toSet());


        List<AnswerResponse> answerResponseList = new ArrayList<>();

        for(Long idtopic : topicId){
            AnswerResponse answerResponse = new AnswerResponse();
            answerResponse.setTopicId(idtopic);
            answerResponse.setUserId(userId);
            List<FieldDTO> fieldDTOS = new ArrayList<>();
            List<AnswerEntity> answerEntities=answerRepository.findByTopic_idAndUser_Id(idtopic,userId);
            for(AnswerEntity item : answerEntities){
                FieldDTO fieldDTO=new FieldDTO();
                fieldDTO.setAnswer(item.getAnsweruser());
                fieldDTO.setQuestionId(item.getQuestion().getId());
                fieldDTOS.add(fieldDTO);
            }
            answerResponse.setAnswers(fieldDTOS);
            answerResponseList.add(answerResponse);
        }
        return answerResponseList;
    }


}
