package com.academia.library.validator;

import com.academia.library.dto.request.TagRequest;
import com.academia.library.exception.TagAlreadyExistException;
import com.academia.library.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagValidator {

    private final TagRepository tagRepository;

    public void validate(TagRequest tagRequest) {
        String tagName = tagRequest.getName();

        if (tagRepository.existsByName(tagName)) {
            throw new TagAlreadyExistException(tagName);
        }
    }
}
