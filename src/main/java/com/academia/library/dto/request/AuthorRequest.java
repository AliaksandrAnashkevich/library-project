package com.academia.library.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthorRequest {

    private String firstName;
    private String lastName;
}