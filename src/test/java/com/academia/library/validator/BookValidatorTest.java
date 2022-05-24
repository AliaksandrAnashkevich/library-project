package com.academia.library.validator;

import com.academia.library.dto.request.BookRequest;
import com.academia.library.exception.AuthorNotFoundException;
import com.academia.library.exception.BookAlreadyExistException;
import com.academia.library.exception.TagNotFoundException;
import com.academia.library.model.Author;
import com.academia.library.model.Book;
import com.academia.library.model.Tag;
import com.academia.library.repository.AuthorRepository;
import com.academia.library.repository.BookRepository;
import com.academia.library.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class BookValidatorTest {

    @Mock
    AuthorRepository authorRepository;

    @Mock
    BookRepository bookRepository;

    @Mock
    TagRepository tagRepository;

    @InjectMocks
    BookValidator bookValidator;

    @Test
    void validateBeforeSaveSuccess() {
        // given
        Long tagId = 1L;
        Long authorId = 1L;
        String title = "Testing book";
        BigDecimal price = new BigDecimal("5.00");

        Author author = new Author();

        Tag tag = new Tag();

        BookRequest bookRequest = BookRequest.builder()
                .title(title)
                .price(price)
                .authorId(authorId)
                .tagsId(List.of(tagId))
                .build();
        // when
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(bookRepository.existsByTitleAndAuthor(title,author)).thenReturn(false);
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));
        // then
        bookValidator.validateBeforeSave(bookRequest);
    }

    @Test
    void validateBeforeUpdateSuccess() {
        // given
        Long tagId = 1L;
        Long authorId = 1L;
        String title = "Testing book";
        BigDecimal price = new BigDecimal("5.00");

        Author author = new Author();
        author.setId(authorId);

        Tag tag = new Tag();

        Book book = new Book();
        book.setTitle("title");
        book.setAuthor(author);

        BookRequest bookRequest = BookRequest.builder()
                .title(title)
                .price(price)
                .authorId(authorId)
                .tagsId(List.of(tagId))
                .build();
        // when
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(bookRepository.existsByTitleAndAuthor(title,author)).thenReturn(false);
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));
        // then
        bookValidator.validateBeforeUpdate(bookRequest, book);
    }

    @Test
    void validateBeforeSaveExceptionShouldThrowAuthorNotFoundException() {
        // given
        Long tagId = 1L;
        Long authorId = 1L;
        String title = "Testing book";
        BigDecimal price = new BigDecimal("5.00");

        Author author = new Author();

        BookRequest bookRequest = BookRequest.builder()
                .title(title)
                .price(price)
                .authorId(authorId)
                .tagsId(List.of(tagId))
                .build();
        // when
        when(authorRepository.findById(2L)).thenReturn(Optional.of(author));
        // then
        assertThrows(AuthorNotFoundException.class, () -> bookValidator.validateBeforeSave(bookRequest));
    }

    @Test
    void validateBeforeSaveExceptionShouldThrowBookAlreadyException() {
        // given
        Long tagId = 1L;
        Long authorId = 1L;
        String title = "Testing book";
        BigDecimal price = new BigDecimal("5.00");

        Author author = new Author();

        BookRequest bookRequest = BookRequest.builder()
                .title(title)
                .price(price)
                .authorId(authorId)
                .tagsId(List.of(tagId))
                .build();
        // when
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(bookRepository.existsByTitleAndAuthor(title,author)).thenReturn(true);
        // then
        assertThrows(BookAlreadyExistException.class, () -> bookValidator.validateBeforeSave(bookRequest));
    }

    @Test
    void validateBeforeSaveExceptionShouldThrowTagNotFoundException() {
        // given
        Long tagId = 1L;
        Long authorId = 1L;
        String title = "Testing book";
        BigDecimal price = new BigDecimal("5.00");

        Author author = new Author();

        Tag tag = new Tag();

        BookRequest bookRequest = BookRequest.builder()
                .title(title)
                .price(price)
                .authorId(authorId)
                .tagsId(List.of(tagId))
                .build();
        // when
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(bookRepository.existsByTitleAndAuthor(title,author)).thenReturn(false);
        when(tagRepository.findById(2L)).thenReturn(Optional.of(tag));
        // then
        assertThrows(TagNotFoundException.class, () -> bookValidator.validateBeforeSave(bookRequest));
    }
}