package com.academia.library.mapper;

import com.academia.library.dto.AuthorRequest;
import com.academia.library.dto.AuthorResponse;
import com.academia.library.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorResponse toDto(Author author);

    Author toEntity(AuthorRequest authorRequest);

    Author updateRequestToEntity(AuthorRequest authorRequest, @MappingTarget Author author);
}
