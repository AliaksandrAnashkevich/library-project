package com.academia.library.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
