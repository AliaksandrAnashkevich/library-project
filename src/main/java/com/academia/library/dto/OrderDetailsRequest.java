package com.academia.library.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class OrderDetailsRequest {
    private final Long id;
    private final OrderDetailRequestDto orderDetailRequestDto;

    @Builder
    @Getter
    @ToString
    public static class OrderDetailRequestDto {
        private final Long bookId;
        private final Long count;
    }
}