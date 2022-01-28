package com.academia.library.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class BookRequestDto {

    private BigDecimal price;
    private String title;
    private Long authorId;
    private List<Long> tagsId;
}
