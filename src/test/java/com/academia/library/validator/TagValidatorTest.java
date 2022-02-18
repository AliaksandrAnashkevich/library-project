package com.academia.library.validator;

import com.academia.library.dto.request.TagRequest;
import com.academia.library.exception.TagAlreadyExistException;
import com.academia.library.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TagValidatorTest {

    @Mock
    TagRepository tagRepository;

    @InjectMocks
    TagValidator tagValidator;

    @Test
    void validateSuccess() {
        // given
        String tagName = "Tag";

        TagRequest tagRequest = new TagRequest();
        tagRequest.setName(tagName);
        // when
        when(tagRepository.existsByName(tagName)).thenReturn(false);
        // then
        tagValidator.validate(tagRequest);
    }

    @Test
    void validateExceptionShouldThrowTagNotFoundException() {
        // given
        String tagName = "Tag";

        TagRequest tagRequest = new TagRequest();
        tagRequest.setName(tagName);
        // when
        when(tagRepository.existsByName(tagName)).thenReturn(true);
        // then
        assertThrows(TagAlreadyExistException.class, () -> tagValidator.validate(tagRequest));
    }
}