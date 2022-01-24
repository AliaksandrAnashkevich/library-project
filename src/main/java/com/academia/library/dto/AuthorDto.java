package com.academia.library.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class AuthorDto {

    private Long id;
    private String firstName;
    private String lastName;
}
