package com.academia.library.mapper;

import com.academia.library.dto.AuthorRequestDto;
import com.academia.library.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toEntity(AuthorRequestDto authorDto);
}
