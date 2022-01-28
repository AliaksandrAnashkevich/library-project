package com.academia.library.mapper;

import com.academia.library.dto.AuthorRequestDto;
import com.academia.library.dto.AuthorResponseDto;
import com.academia.library.model.Author;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthorMapper {

    AuthorResponseDto toDto(Author author);

    Author toEntity(AuthorRequestDto authorDto);

    Author updateRequestToEntity(AuthorRequestDto authorRequestDto, @MappingTarget Author author);
}
