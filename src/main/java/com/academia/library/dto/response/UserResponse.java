package com.academia.library.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class UserResponse {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final LocalDateTime createAt;
    private final LocalDateTime updateAt;
}