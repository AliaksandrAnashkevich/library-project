package com.academia.library.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserRequestDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
