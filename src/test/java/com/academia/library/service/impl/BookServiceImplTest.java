package com.academia.library.service.impl;

import com.academia.library.dto.request.BookRequest;
import com.academia.library.dto.response.BookResponse;
import com.academia.library.mapper.BookMapper;
import com.academia.library.model.Book;
import com.academia.library.repository.BookRepository;
import com.academia.library.validator.BookValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class BookServiceImplTest {

    @Mock
    BookMapper bookMapper;

    @Mock
    BookValidator bookValidator;

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookServiceImpl bookService;

    @DisplayName("Get book by id")
    @Test
    void findById() {
        // given
        var id = 1L;

        var extend = new Book();
        extend.setId(id);
        extend.setTitle("Title");
        extend.setPrice(new BigDecimal("9.99"));

        var bookResponse = BookResponse.builder()
                .title("Title")
                .price(new BigDecimal("9.99"))
                .build();
        // when
        when(bookRepository.findById(id)).thenReturn(Optional.of(extend));
        when(bookMapper.toDto(extend)).thenReturn(bookResponse);
        var actual = bookService.findById(id);
        // then
        assertThat(actual.getPrice()).isEqualTo(extend.getPrice());
        assertThat(actual.getTitle()).isEqualTo(extend.getTitle());
    }

    @DisplayName("Get all books")
    @Test
    void findAll() {
        // given
        var id = 1L;

        var book = new Book();
        book.setId(id);
        book.setTitle("Title");
        book.setPrice(new BigDecimal("9.99"));

        var bookResponse = BookResponse.builder()
                .title("Title")
                .price(new BigDecimal("9.99"))
                .build();
        // when
        when(bookRepository.findAll()).thenReturn(List.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookResponse);
        var actual = bookService.findAll();
        // then
        assertTrue(actual.size() > 0);
    }

    @DisplayName("Create new book")
    @Test
    void create() {
        // given
        var id = 1L;

        var book = new Book();
        book.setId(id);
        book.setTitle("Title");
        book.setPrice(new BigDecimal("9.99"));

        var extend = BookRequest.builder()
                .title("Title")
                .price(new BigDecimal("9.99"))
                .build();
