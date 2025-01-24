package com.project01.quiz.controller;


import com.project01.quiz.dto.response.ApiResponse;
import com.project01.quiz.dto.response.UserResponseDTO;
import com.project01.quiz.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ApiResponse<UserResponseDTO> getMyInfo(@PathVariable Long id) {
        UserResponseDTO userResponseDTO=userServices.getMyInfo(id);
        return ApiResponse.<UserResponseDTO>builder()
                .code(HttpStatus.OK)
                .message("Success !")
                .data(userResponseDTO)
                .build();
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ApiResponse updatePassword(@PathVariable Long id,@RequestBody UserResponseDTO userResponseDTO) {
        userServices.updateUser(id,userResponseDTO);
        return ApiResponse.<UserResponseDTO>builder()
                .code(HttpStatus.OK)
                .message("Success !")
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse deleteByIds(@PathVariable List<Long> id) {
        userServices.deleteByIds(id);
        return ApiResponse.builder()
                .code(HttpStatus.OK)
                .message("Success !")
                .build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> userResponseDTOS= userServices.getAllUsers();
        return ApiResponse.<List<UserResponseDTO>>builder()
                .code(HttpStatus.OK)
                .data(userResponseDTOS)
                .message("Success !")
                .build();
    }


}
