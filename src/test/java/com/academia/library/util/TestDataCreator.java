package com.academia.library.util;

import com.academia.library.dto.request.AuthRequest;
import com.academia.library.dto.request.AuthorRequest;
import com.academia.library.dto.request.TagRequest;
import com.academia.library.dto.request.UserRequest;

public class TestDataCreator {
    public static TagRequest TEST_TAG = createTestTag();

    public static AuthorRequest TEST_AUTHOR = AuthorRequest.builder()
            .firstName("firstName")
            .lastName("lastName")
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
    public static String TEST_BOOK_TITLE = "Abstract name";

    public static TagRequest createTestTag() {
        TagRequest tagRequest = new TagRequest();
        tagRequest.setName("Abstract name");
        return tagRequest;
    }
}
