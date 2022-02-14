package com.academia.library.dto.responce;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthorResponse {

    private String firstName;
    private String lastName;
}