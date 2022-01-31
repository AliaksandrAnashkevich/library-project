package com.academia.library.mapper;

import com.academia.library.dto.TagRequestDto;
import com.academia.library.dto.TagResponseDto;
import com.academia.library.model.Tag;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagResponseDto toDto(Tag tag);

    Tag toEntity(TagRequestDto tagDto);

    Tag updateRequestToEntity(TagRequestDto tagDto, @MappingTarget Tag tag);
}
