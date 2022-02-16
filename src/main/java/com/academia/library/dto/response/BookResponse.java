package com.academia.library.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
public class BookResponse {

    private BigDecimal price;
    private String title;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private AuthorResponse author;
    private Set<TagResponse> tags;
}