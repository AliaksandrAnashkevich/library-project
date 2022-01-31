package com.academia.library.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class BookRequestDto {

    private final BigDecimal price;
    private final String title;
    private final Long authorId;
    private final List<Long> tagsId;
}
