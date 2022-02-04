package com.academia.library.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderDetailsRequest {

    private final Long id;
    private final Long bookId;
    private final Long count;
}
