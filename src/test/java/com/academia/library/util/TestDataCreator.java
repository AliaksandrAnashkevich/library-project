package com.academia.library.util;

import com.academia.library.dto.AuthorRequest;
import com.academia.library.dto.TagRequest;

import java.math.BigDecimal;

public class TestDataCreator {
    public static TagRequest TEST_TAG = createTestTag();

    public static AuthorRequest TEST_AUTHOR = AuthorRequest.builder()
            .firstName("firstName")
            .lastName("lastName")
            .build();

    public static String TEST_BOOK_TITLE = "Abstract name";

    public static TagRequest createTestTag() {
        TagRequest tagRequestDto = new TagRequest();
        tagRequestDto.setName("Abstract name");
        return tagRequestDto;
    }
}
