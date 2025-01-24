package com.project01.quiz.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;

    private String username;

    private String password;

    private String passwordNew;

    private String fullname;

    private String email;

    private Date birth;

    private List<String> role=new ArrayList<>();
}
