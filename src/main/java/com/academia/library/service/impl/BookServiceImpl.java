package com.academia.library.service.impl;

import com.academia.library.dto.BookRequest;
import com.academia.library.dto.BookResponse;
import com.academia.library.exception.BookNotFoundException;
import com.academia.library.mapper.BookMapper;
import com.academia.library.model.Book;
import com.academia.library.repository.BookRepository;
import com.academia.library.service.BookService;
import com.academia.library.validator.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final BookValidator bookValidator;

    @Override
    @Transactional(readOnly = true)
    public BookResponse findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        return bookMapper.toDto(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> findAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookResponse create(BookRequest bookRequest) {

        bookValidator.validatorAuthorAndTitle(bookRequest.getAuthorId(), bookRequest.getTitle());
        bookValidator.validationTags(bookRequest.getTagsId());

        Book book = bookMapper.toEntity(bookRequest);
        Book savedBook = bookRepository.save(book);

        return bookMapper.toDto(savedBook);
    }

    @Override
    @Transactional
    public BookResponse update(Long id, BookRequest bookRequest) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        bookValidator.validatorAuthorAndTitleExceptThisBook(bookRequest, book);
        bookValidator.validationTags(bookRequest.getTagsId());

        book = bookMapper.mapRequestToEntity(bookRequest, book);
        Book updatedBook = bookRepository.save(book);

        return bookMapper.toDto(updatedBook);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        bookRepository.delete(book);
    }
}
