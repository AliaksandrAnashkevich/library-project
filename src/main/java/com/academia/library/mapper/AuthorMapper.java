package com.academia.library.mapper;

import com.academia.library.dto.request.AuthorRequest;
import com.academia.library.dto.response.AuthorResponse;
import com.academia.library.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorResponse toDto(Author author);

    Author toEntity(AuthorRequest authorRequest);

    Author updateRequestToEntity(AuthorRequest authorRequest, @MappingTarget Author author);
}
