package com.academia.library.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TagResponseDto {

    private final Long id;
    private final String name;
}
