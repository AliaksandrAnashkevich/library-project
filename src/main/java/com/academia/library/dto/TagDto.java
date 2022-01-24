package com.academia.library.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class TagDto {

    private Long id;
    private String name;
}
