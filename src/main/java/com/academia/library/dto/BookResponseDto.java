package com.academia.library.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
public class BookResponseDto {

    private final Long id;
    private final BigDecimal price;
    private final String title;
    private final LocalDateTime createAt;
    private final LocalDateTime updateAt;
    private final AuthorResponseDto author;
    private final Set<TagResponseDto> tags;
}
