package com.project01.quiz.services;

import com.project01.quiz.dto.response.UserResponseDTO;
import com.project01.quiz.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserServices {
    UserResponseDTO getMyInfo(Long id);
    void updateUser(Long id,UserResponseDTO userResponseDTO);
    List<UserResponseDTO> getAllUsers();
    void deleteByIds(List<Long> ids);
}
