package com.academia.library.mapper;

import com.academia.library.dto.TagDto;
import com.academia.library.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    TagDto toDto(Tag tag);

    Tag toEntity(TagDto tagDto);
}
