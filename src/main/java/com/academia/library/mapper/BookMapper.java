package com.academia.library.mapper;

import com.academia.library.dto.BookResponse;
import com.academia.library.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE= Mappers.getMapper(BookMapper.class);

    BookResponse toDto(Book book);
}
