package com.academia.library.mapper;

import com.academia.library.dto.BookDto;
import com.academia.library.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "book.author", target = "author")
    @Mapping(source = "book.tags", target = "tags")
    BookDto toDto(Book book);

    @Mapping(source = "bookDto.author", target = "author")
    @Mapping(source = "bookDto.tags", target = "tags")
    Book toEntity(BookDto bookDto);
}
