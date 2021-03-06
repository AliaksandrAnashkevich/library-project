package com.academia.library.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class UserResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}