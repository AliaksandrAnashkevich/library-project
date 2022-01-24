package com.academia.library.mapper;

import com.academia.library.dto.BookDto;
import com.academia.library.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto toDto(Book book);

    Book toEntity(BookDto bookDto);
}
