package com.academia.library.mapper;

import com.academia.library.dto.BookDto;
import com.academia.library.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "book.author", target = "author")
    @Mapping(source = "book.tags", target = "tags")
    BookDto toDto(Book book);
}
