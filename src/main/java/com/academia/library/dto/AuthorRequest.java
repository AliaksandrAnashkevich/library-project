package com.academia.library.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthorRequest {

    private String firstName;
    private String lastName;
}