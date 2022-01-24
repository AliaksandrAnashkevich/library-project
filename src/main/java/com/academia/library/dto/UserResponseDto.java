package com.academia.library.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class UserResponseDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
