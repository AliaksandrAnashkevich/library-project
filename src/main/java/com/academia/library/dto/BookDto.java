package com.academia.library.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class BookDto {

    private Long id;
    private BigDecimal price;
    private String title;
    private String imageUrl;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private AuthorDto author;
    private Set<TagDto> tags;
}
