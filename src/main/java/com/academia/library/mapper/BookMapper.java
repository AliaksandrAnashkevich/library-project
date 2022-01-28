package com.academia.library.mapper;

import com.academia.library.dto.BookRequestDto;
import com.academia.library.dto.BookResponseDto;
import com.academia.library.model.Book;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookMapper {

    BookResponseDto toDto(Book book);

    Book toEntity(BookRequestDto bookResponseDto);

    Book updateRequestToEntity(BookRequestDto bookResponseDto, @MappingTarget Book book);
}
