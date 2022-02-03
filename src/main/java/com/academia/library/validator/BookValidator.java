package com.academia.library.validator;

import com.academia.library.dto.BookRequest;
import com.academia.library.exception.AuthorNotFoundException;
import com.academia.library.exception.BookAlreadyExistException;
import com.academia.library.exception.TagNotFoundException;
import com.academia.library.model.Author;
import com.academia.library.model.Book;
import com.academia.library.repository.AuthorRepository;
import com.academia.library.repository.BookRepository;
import com.academia.library.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final TagRepository tagRepository;

    public void validatorAuthorAndTitle(Long authorId, String title) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId));

        if (bookRepository.existsByTitleAndAuthor(title, author)) {
            throw new BookAlreadyExistException();
        }
    }

    public void validatorAuthorAndTitleExceptThisBook(BookRequest bookRequest, Book book) {
        if (!(book.getAuthor().getId().equals(bookRequest.getAuthorId())
                && book.getTitle().equals(bookRequest.getTitle()))) {
            validatorAuthorAndTitle(bookRequest.getAuthorId(), bookRequest.getTitle());
        }
    }

    public void validationTags(List<Long> tagsId) {
        tagsId.forEach(id -> tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException(id)));
    }
}
