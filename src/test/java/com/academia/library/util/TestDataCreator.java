package com.academia.library.util;

import com.academia.library.dto.AuthorRequestDto;
import com.academia.library.dto.BookRequestDto;
import com.academia.library.dto.TagRequestDto;

import java.math.BigDecimal;

public class TestDataCreator {
    public static TagRequestDto TEST_TAG = createTestTag();

    public static AuthorRequestDto TEST_AUTHOR = AuthorRequestDto.builder()
            .firstName("firstName")
            .lastName("lastName")
            .build();

    public static String TEST_BOOK_TITLE = "Abstract name";

    public static BigDecimal TEST_BOOK_PRICE = new BigDecimal("10.00");

    public static TagRequestDto createTestTag() {
        TagRequestDto tagRequestDto = new TagRequestDto();
        tagRequestDto.setName("Abstract name");
        return tagRequestDto;
    }
}
