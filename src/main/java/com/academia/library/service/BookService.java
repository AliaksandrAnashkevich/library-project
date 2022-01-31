package com.academia.library.service;

import com.academia.library.dto.BookRequestDto;
import com.academia.library.dto.BookResponseDto;
import com.academia.library.model.Book;

import java.util.List;

public interface BookService {

    BookResponseDto findById(Long id);

    List<BookResponseDto> findAll();

    BookResponseDto create(BookRequestDto bookRequestDto);

    BookResponseDto update(Long id, BookRequestDto bookRequestDto);

    void delete(Long id);

    Book getBookOrThrowException(Long id);
}
