package com.academia.library.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class BookDto {

    private Long id;
    private BigDecimal price;
    private String title;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
