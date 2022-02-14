package com.academia.library.dto.responce;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
public class AuthResponse {

    private String email;
    private String token;
}