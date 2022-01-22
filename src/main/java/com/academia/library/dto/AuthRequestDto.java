package com.academia.library.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthRequestDto {

    private String email;
    private String password;
}
