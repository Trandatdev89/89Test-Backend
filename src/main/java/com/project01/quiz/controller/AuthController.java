package com.project01.quiz.controller;


import com.project01.quiz.dto.request.AuthRequest;
import com.project01.quiz.dto.request.RegisterRequest;
import com.project01.quiz.dto.request.TokenRequest;
import com.project01.quiz.dto.response.ApiResponse;
import com.project01.quiz.dto.response.AuthResponse;
import com.project01.quiz.dto.response.IntrospecResponse;
import com.project01.quiz.services.AuthServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServices authServices;

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse=authServices.authenticate(authRequest);
        return ApiResponse.<AuthResponse>builder()
                .code(HttpStatus.OK)
                .message("Success !")
                .data(authResponse)
                .build();
    }

    @PostMapping("/introspectoken")
    public ApiResponse<IntrospecResponse> introspecToken(@RequestBody @Valid TokenRequest tokenRequest) {
        IntrospecResponse intro=authServices.introspecToken(tokenRequest);
        return ApiResponse.<IntrospecResponse>builder()
                .code(HttpStatus.OK)
                .message("Success !")
                .data(intro)
                .build();
    }

    @PostMapping("/refresh-token")
    public ApiResponse<AuthResponse> refreshToken(@RequestBody @Valid TokenRequest tokenRequest) {
        AuthResponse intro=authServices.refreshToken(tokenRequest);
        return ApiResponse.<AuthResponse>builder()
                .code(HttpStatus.OK)
                .message("Success !")
                .data(intro)
                .build();
    }


    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody @Valid TokenRequest tokenRequest){
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK)
                .message("Success !")
                .data(authServices.logout(tokenRequest))
                .build();
    }

    @PostMapping("/register")
    public ApiResponse register(@RequestBody @Valid RegisterRequest registerRequest) {
        authServices.register(registerRequest);
        return ApiResponse.builder()
                .code(HttpStatus.OK)
                .message("success!")
                .build();
    }

}
