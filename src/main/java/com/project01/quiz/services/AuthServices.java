package com.project01.quiz.services;

import com.nimbusds.jose.JOSEException;
import com.project01.quiz.dto.request.AuthRequest;
import com.project01.quiz.dto.request.RegisterRequest;
import com.project01.quiz.dto.request.TokenRequest;
import com.project01.quiz.dto.response.AuthResponse;
import com.project01.quiz.dto.response.IntrospecResponse;
import com.project01.quiz.dto.response.UserResponseDTO;

import java.text.ParseException;

public interface AuthServices {
    AuthResponse authenticate(AuthRequest authRequest);
    IntrospecResponse introspecToken(TokenRequest tokenRequest);
    AuthResponse refreshToken(TokenRequest tokenRequest);
    Void logout(TokenRequest tokenRequest);
    void register(RegisterRequest registerRequest);
}
