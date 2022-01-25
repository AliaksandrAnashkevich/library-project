package com.academia.library.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
public class BookResponseDto {

    private BigDecimal price;
    private String title;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private AuthorResponseDto author;
    private Set<TagResponseDto> tags;
}
