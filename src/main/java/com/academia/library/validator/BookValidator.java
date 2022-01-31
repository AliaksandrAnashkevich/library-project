package com.academia.library.validator;

import com.academia.library.exception.AuthorNotFoundException;
import com.academia.library.exception.BookAlreadyExistException;
import com.academia.library.exception.TagNotFoundException;
import com.academia.library.repository.AuthorRepository;
import com.academia.library.repository.BookRepository;
import com.academia.library.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final TagRepository tagRepository;

    public void validatorAuthorAndTitleForCreateBook(Long authorId, String title) {
        if (authorRepository.existsById(authorId)) {
            if (bookRepository.existsByTitleAndAuthorId(title, authorId)) {
                throw new BookAlreadyExistException();
            }
        } else {
            throw new AuthorNotFoundException(authorId);
        }
    }

    public void validatorAuthorAndTitleForUpdateBook(Long bookId, Long authorId, String title) {
        if (authorRepository.existsById(authorId)) {
            if (bookRepository.existsByTitleAndAuthorIdAndIdNot(title, authorId, bookId)) {
                throw new BookAlreadyExistException();
            }
        } else {
            throw new AuthorNotFoundException(authorId);
        }
    }

    public void validationTags(List<Long> tagsId) {
        List<Long> notExistTags = tagsId.stream()
                .filter(Predicate.not(tagRepository::existsById))
                .collect(Collectors.toList());

        if (!notExistTags.isEmpty()) {
            throw new TagNotFoundException(notExistTags.stream().findFirst().get());
        }
    }
}
