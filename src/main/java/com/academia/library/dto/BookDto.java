package com.academia.library.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
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
