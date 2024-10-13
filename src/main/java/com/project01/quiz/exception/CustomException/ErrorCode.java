package com.project01.quiz.exception.CustomException;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    USER_EXITED("Username Exited",HttpStatus.BAD_REQUEST),
    USER_NOTFOUND("User Not Found",HttpStatus.NOT_FOUND),
    INVALID_TOKEN("Token invalidate",HttpStatus.UNAUTHORIZED),
    PASSWORD_WRONG("Password wrong.Please fill password again !",HttpStatus.BAD_REQUEST),
    UNAUTHOUCATED("Tài khoản chưa được xác thực,Yêu cầu đăng nhập!",HttpStatus.UNAUTHORIZED),
    BAD_REQUEST("Dữ liệu chưa hợp lệ!",HttpStatus.BAD_REQUEST),
    FOBIDEN("Bạn không có quyền truy cập vào tài nguyên này",HttpStatus.FORBIDDEN);
    private String message;
    private HttpStatus code;

    ErrorCode(String message, HttpStatus code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }
}
