package com.academia.library.util;

import com.academia.library.dto.AuthRequest;
import com.academia.library.dto.AuthorRequest;
import com.academia.library.dto.BookRequest;
import com.academia.library.dto.OrderDetailsRequest;
import com.academia.library.dto.OrderRequest;
import com.academia.library.dto.TagRequest;
import com.academia.library.dto.UserRequest;
import com.academia.library.model.OrderDetail;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TestDataCreator {
    public static TagRequest TEST_TAG = TagRequest.builder()
            .name("Abstract name")
            .build();

    public static AuthorRequest TEST_AUTHOR = AuthorRequest.builder()
            .firstName("firstName")
            .lastName("lastName")
            .build();

    public static BookRequest TEST_BOOK = BookRequest.builder()
            .price(new BigDecimal("9.99"))
            .title("Abstract name")
            .createAt(LocalDateTime.now())
            .updateAt(LocalDateTime.now())
            .build();

    public static AuthRequest LOGIN_TEST_USER = AuthRequest.builder()
            .email("alex@example.com")
            .password("Aa123456")
            .build();

    public static UserRequest INSERT_USER = UserRequest.builder()
            .firstName("Alexandr")
            .lastName("Anashkevich")
            .email("alex@example.com")
            .password("Aa123456")
            .build();

    public static UserRequest VALID_TEST_USER = UserRequest.builder()
            .firstName("Alexandr")
            .lastName("Anashkevich")
            .email("test@example.com")
            .password("Aa123456")
            .build();

    public static UserRequest INVALID_TEST_USER = UserRequest.builder()
            .firstName("Alexandr")
            .lastName("Anashkevich")
            .email("testexample.com")
            .password("aa123456")
            .build();
}