package com.academia.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long id;
    private BigDecimal price;
    private String title;
    private String imageUrl;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}