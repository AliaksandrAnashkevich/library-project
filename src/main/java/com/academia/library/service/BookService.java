package com.academia.library.service;

import com.academia.library.dto.BookDto;
import java.util.List;

public interface BookService {

    BookDto findById(Long id);

    List<BookDto> findAll();
}
