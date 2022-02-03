package com.academia.library.validator;

import com.academia.library.dto.AuthorRequest;
import com.academia.library.exception.AuthorAlreadyExistException;
import com.academia.library.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorValidator {

    private final AuthorRepository authorRepository;

    public void validate(AuthorRequest authorRequest) {
        String firstName = authorRequest.getFirstName();
        String lastName = authorRequest.getLastName();

        if (authorRepository.existsByFirstNameAndLastName(firstName, lastName)) {
            throw new AuthorAlreadyExistException(firstName, lastName);
        }
    }
}
