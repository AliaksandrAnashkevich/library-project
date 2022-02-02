package com.academia.library.service;

import com.academia.library.dto.BookRequest;
import com.academia.library.dto.BookResponse;
import com.academia.library.model.Book;

import java.util.List;

public interface BookService {

    BookResponse findById(Long id);

    List<BookResponse> findAll();

    BookResponse create(BookRequest bookRequest);

    BookResponse update(Long id, BookRequest bookRequest);

    void delete(Long id);
}
