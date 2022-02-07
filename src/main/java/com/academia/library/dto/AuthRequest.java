package com.academia.library.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;

@Builder
@Getter
public class AuthRequest {

    @Email
    private String email;
    private String password;
}