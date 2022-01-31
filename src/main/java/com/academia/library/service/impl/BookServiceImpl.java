package com.academia.library.service.impl;

import com.academia.library.dto.BookRequestDto;
import com.academia.library.dto.BookResponseDto;
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
    public BookResponseDto findById(Long id) {
        Book book = getBookOrThrowException(id);
        return bookMapper.toDto(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> findAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDto create(BookRequestDto bookRequestDto) {
        bookValidator.validatorAuthorAndTitleForCreateBook(bookRequestDto.getAuthorId(), bookRequestDto.getTitle());
        bookValidator.validationTags(bookRequestDto.getTagsId());

        Book book = bookMapper.toEntity(bookRequestDto);
        Book createBook = bookRepository.save(book);

        return bookMapper.toDto(createBook);
    }

    @Override
    public BookResponseDto update(Long id, BookRequestDto bookRequestDto) {
        bookValidator.validatorAuthorAndTitleForUpdateBook(id, bookRequestDto.getAuthorId(), bookRequestDto.getTitle());
        bookValidator.validationTags(bookRequestDto.getTagsId());

        Book book = bookMapper.mapRequestToEntity(bookRequestDto, getBookOrThrowException(id));
        Book createBook = bookRepository.save(book);

        return bookMapper.toDto(createBook);
    }

    @Override
    public void delete(Long id) {
        Book book = getBookOrThrowException(id);
        bookRepository.delete(book);
    }

    public Book getBookOrThrowException(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }


}
