package com.academia.library.validator;

import com.academia.library.dto.request.BookRequest;
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

    public void validateBeforeSave(BookRequest bookRequest) {
        validate(bookRequest);
    }

    public void validateBeforeUpdate(BookRequest bookRequest, Book book) {
        boolean isEqualsAuthorId = book.getAuthor().getId().equals(bookRequest.getAuthorId());
        boolean isEqualsTitle = book.getTitle().equals(bookRequest.getTitle());

        if (!(isEqualsAuthorId && isEqualsTitle)) {
            validate(bookRequest);
        }
    }

    private void validate(BookRequest bookRequest) {
        Long authorId = bookRequest.getAuthorId();
        String title = bookRequest.getTitle();
        List<Long> tagIds = bookRequest.getTagsId();

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId));

        if (bookRepository.existsByTitleAndAuthor(title, author)) {
            throw new BookAlreadyExistException();
        }
        validateTags(tagIds);
    }

    private void validateTags(List<Long> tagIds) {
        tagIds.forEach(id -> tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException(id)));
    }
}
