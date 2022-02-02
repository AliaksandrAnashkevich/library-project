package com.academia.library.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Builder
@Getter
public class UserRequest {

    @Pattern(regexp = "[A-Z][a-z]*")
    private String firstName;

    @Pattern(regexp = "[A-Z][a-z]*")
    private String lastName;

    @Email
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$")
    private String password;
}