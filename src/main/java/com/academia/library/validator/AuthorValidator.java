package com.academia.library.validator;

import com.academia.library.exception.AuthorAlreadyExistException;
import com.academia.library.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorValidator {

    private final AuthorRepository authorRepository;

    public void validatorByName(String firstName, String lastName){
        if (authorRepository.existsByFirstNameAndLastName(firstName, lastName)) {
            throw new AuthorAlreadyExistException(firstName, lastName);
        }
    }
}
