package com.academia.library.mapper;

import com.academia.library.dto.request.BookRequest;
import com.academia.library.dto.response.BookResponse;
import com.academia.library.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "book.author", target = "author")
    @Mapping(source = "book.tags", target = "tags")
    BookResponse toDto(Book book);

    Book toEntity(BookRequest bookRequest);
}