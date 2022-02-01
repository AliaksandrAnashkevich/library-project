package com.academia.library.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
public class AuthResponse {

    private String email;
    private String token;
}