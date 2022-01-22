package com.academia.library.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponseDto {

    private String email;
    private String token;
}
