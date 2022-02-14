package com.academia.library.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TagRequest {

    private String name;
}