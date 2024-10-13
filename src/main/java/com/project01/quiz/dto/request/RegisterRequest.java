package com.project01.quiz.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "username thì phải 4 ký tự trở lên ")
    @Size(min = 4, message = "Name must be at least 4 characters long")
    private String username;

    @NotBlank(message = "passowrd thì phải có 8 ký tự tro lên ")
    private String password;

    private String email;

    private String fullname;

    private Date birth;

    private String role;
}
