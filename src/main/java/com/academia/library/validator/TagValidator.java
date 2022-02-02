package com.academia.library.validator;

import com.academia.library.exception.TagAlreadyExistException;
import com.academia.library.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagValidator {

    private final TagRepository tagRepository;

    public void validatorByName(String name) {
        if (tagRepository.existsByName(name)) {
            throw new TagAlreadyExistException(name);
        }
    }
}
