package com.project01.quiz.services.impl;

import com.project01.quiz.dto.response.UserResponseDTO;
import com.project01.quiz.entity.QuestionEntity;
import com.project01.quiz.entity.TopicEntity;
import com.project01.quiz.entity.UserEntity;
import com.project01.quiz.exception.CustomException.AppException;
import com.project01.quiz.exception.CustomException.ErrorCode;
import com.project01.quiz.repository.UserRepository;
import com.project01.quiz.services.UserServices;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO getMyInfo(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOTFOUND));
        UserResponseDTO userResponseDTO=modelMapper.map(user, UserResponseDTO.class);
        List<String> roles=user.getRoles().stream().map(item->item.getCode().toString()).toList();
        userResponseDTO.setRole(roles);
        return userResponseDTO;
    }

    @Override
    public void updateUser(Long id, UserResponseDTO userResponseDTO) {
        UserEntity user = userRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOTFOUND));
        if(passwordEncoder.matches(userResponseDTO.getPassword(),user.getPassword())) {
            user.setPassword(passwordEncoder.encode(userResponseDTO.getPasswordNew()));
        }
        userRepository.save(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<UserEntity> users = userRepository.findByRoles_Code("USER");
        List<UserResponseDTO> userResponseDTOS=new ArrayList<>();
        for (UserEntity user : users) {
            UserResponseDTO userResponseDTO=modelMapper.map(user, UserResponseDTO.class);
            userResponseDTO.setRole(user.getRoles().stream().map(item->item.getCode().toString()).toList());
            userResponseDTOS.add(userResponseDTO);
        }
        return userResponseDTOS;
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        userRepository.deleteByIdIn(ids);
    }

}
