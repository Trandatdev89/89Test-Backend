package com.project01.quiz.exception;


import com.project01.quiz.dto.response.ApiResponse;
import com.project01.quiz.exception.CustomException.AppException;
import com.project01.quiz.exception.CustomException.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse> CustomException(AppException e) {
       ErrorCode errorResponse = e.getErrorCode();
       ApiResponse apiResponse = ApiResponse.builder()
               .message(errorResponse.getMessage())
               .code(errorResponse.getCode())
               .build();
       return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleException(MethodArgumentNotValidException ex) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getBindingResult().getFieldError().getDefaultMessage())
                .code(HttpStatus.UNAUTHORIZED)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> AccessDenied(AccessDeniedException ex) {
        ErrorCode errorCode=ErrorCode.FOBIDEN;
        ApiResponse apiResponse = ApiResponse.builder()
                .message(errorCode.getMessage())
                .code(errorCode.getCode())
                .build();
        return  ResponseEntity.status(errorCode.getCode()).body(apiResponse);
    }

}
