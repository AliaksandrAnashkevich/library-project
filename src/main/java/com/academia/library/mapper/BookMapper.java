package com.academia.library.mapper;

import com.academia.library.dto.BookRequestDto;
import com.academia.library.dto.BookResponseDto;
import com.academia.library.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "book.author", target = "author")
    @Mapping(source = "book.tags", target = "tags")
    BookResponseDto toDto(Book book);

    Book toEntity(BookRequestDto bookResponseDto);
}
