package com.academia.library.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderDetailsResponse {

    private final Long id;
    private final Long count;
    private final Long bookId;
}
