package com.academia.library.service;

import com.academia.library.dto.responce.BookResponse;

import java.util.List;

public interface BookService {

    BookResponse findById(Long id);

    List<BookResponse> findAll();
}