package com.academia.library.service;

import com.academia.library.dto.request.BookRequest;
import com.academia.library.dto.response.BookResponse;

import java.util.List;

public interface BookService {

    BookResponse findById(Long id);

    List<BookResponse> findAll();

    BookResponse create(BookRequest bookRequest);

    BookResponse update(Long id, BookRequest bookRequest);

    void delete(Long id);
}
