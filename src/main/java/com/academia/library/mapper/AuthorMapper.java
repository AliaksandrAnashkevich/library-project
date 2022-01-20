package com.academia.library.mapper;

import com.academia.library.dto.AuthorDto;
import com.academia.library.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDto toDto(Author author);

    Author toEntity(AuthorDto authorDto);
}
