package com.academia.library.mapper;

import com.academia.library.dto.request.AuthorRequest;
import com.academia.library.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toEntity(AuthorRequest authorRequest);
}