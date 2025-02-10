package com.project01.quiz.services.impl;

import com.project01.quiz.dto.request.TopicRequest;
import com.project01.quiz.dto.response.TopicResponse;
import com.project01.quiz.entity.TopicEntity;
import com.project01.quiz.repository.QuestionRepository;
import com.project01.quiz.repository.TopicRepository;
import com.project01.quiz.services.TopicServices;
import com.project01.quiz.utils.UploadFile;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class TopicServicesImpl implements TopicServices {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UploadFile uploadFile;

    @Value("${server.port}")
    private Long port;

    @Override
    public List<TopicResponse> getAllTopics() {
        List<TopicEntity> topicEntities = topicRepository.findAll();
        List<TopicResponse> topicResponses = new ArrayList<>();
        for (TopicEntity topicEntity : topicEntities) {
            TopicResponse topicResponse = modelMapper.map(topicEntity, TopicResponse.class);
            topicResponses.add(topicResponse);
        }
        return topicResponses;
    }

    @Override
    public TopicResponse getTopicById(Long id) {
        TopicEntity topicEntities = topicRepository.findById(id).get();
        TopicResponse topicResponse = modelMapper.map(topicEntities, TopicResponse.class);
        return topicResponse;
    }

    @Override
    public TopicResponse createTopic(TopicRequest topicRequest) {
        TopicEntity topicEntity = modelMapper.map(topicRequest, TopicEntity.class);
        String fileName=uploadFile.uploadFiles(topicRequest.getThumnail());
        topicEntity.setThumnail(fileName);
        TopicEntity topic= topicRepository.save(topicEntity);
        TopicResponse topicResponse = modelMapper.map(topic, TopicResponse.class);
        return topicResponse;
    }

    @Override
    public void deleteTopic(List<Long> id) {
        topicRepository.deleteByIdIn(id);
        questionRepository.deleteByTopic_IdIn(id);
    }
}
