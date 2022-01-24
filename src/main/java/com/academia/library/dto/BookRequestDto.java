package com.academia.library.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
public class BookRequestDto {

    private BigDecimal price;
    private String title;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
