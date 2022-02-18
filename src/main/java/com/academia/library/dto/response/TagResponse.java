package com.academia.library.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TagResponse {

    private final Long id;
    private final String name;
}
