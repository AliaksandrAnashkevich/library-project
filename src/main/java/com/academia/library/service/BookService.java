package com.academia.library.service;

import com.academia.library.dto.BookResponseDto;

import java.util.List;

public interface BookService {

    BookResponseDto findById(Long id);

    List<BookResponseDto> findAll();
}
