package com.academia.library.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
public class AuthResponseDto {

    private String email;
    private String token;
}
