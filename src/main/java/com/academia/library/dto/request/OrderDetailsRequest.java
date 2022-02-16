package com.academia.library.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderDetailsRequest {
    private final Long id;
    private final OrderDetailRequestDto orderDetailRequestDto;

    @Builder
    @Getter
    public static class OrderDetailRequestDto {
        private final Long bookId;
        private final Long count;
    }
}