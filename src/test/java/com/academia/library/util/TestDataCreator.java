package com.academia.library.util;

import com.academia.library.dto.AuthorRequestDto;
import com.academia.library.dto.AuthorResponseDto;
import com.academia.library.dto.BookRequestDto;
import com.academia.library.dto.BookResponseDto;
import com.academia.library.dto.TagRequestDto;
import com.academia.library.dto.TagResponseDto;
import com.academia.library.dto.UserRequestDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestDataCreator {
    public static TagRequestDto TEST_TAG = TagRequestDto.builder()
            .name("Abstract name")
            .build();

    public static AuthorRequestDto TEST_AUTHOR = AuthorRequestDto.builder()
            .firstName("firstName")
            .lastName("lastName")
            .build();

    public static BookRequestDto TEST_BOOK = BookRequestDto.builder()
            .price(new BigDecimal("10.00"))
            .title("Abstract name")
            .createAt(LocalDateTime.now())
            .updateAt(LocalDateTime.now())
            .build();

    public static UserRequestDto VALID_TEST_USER = UserRequestDto.builder()
            .firstName("Alexandr")
            .lastName("Anashkevich")
            .email("alex@example.com")
            .password("Aa123456")
            .build();
}
