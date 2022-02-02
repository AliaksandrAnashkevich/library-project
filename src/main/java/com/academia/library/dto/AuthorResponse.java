package com.academia.library.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthorResponse {

    private final Long id;
    private final String firstName;
    private final String lastName;
}
