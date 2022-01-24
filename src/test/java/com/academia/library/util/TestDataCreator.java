package com.academia.library.util;

import com.academia.library.dto.AuthorDto;
import com.academia.library.dto.BookDto;
import com.academia.library.dto.TagDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;

public class TestDataCreator {
    public static TagDto TEST_TAG = TagDto.builder()
            .name("Abstract name")
            .build();

    public static AuthorDto TEST_AUTHOR = AuthorDto.builder()
            .firstName("firstName")
            .lastName("lastName")
            .build();

    public static BookDto TEST_BOOK = BookDto.builder()
            .price(new BigDecimal("10.00"))
            .title("Abstract name")
            .createAt(LocalDateTime.now())
            .updateAt(LocalDateTime.now())
            .build();
}
