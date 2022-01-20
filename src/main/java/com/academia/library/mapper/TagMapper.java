package com.academia.library.mapper;

import com.academia.library.dto.TagDto;
import com.academia.library.model.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDto toDto(Tag tag);

    Tag toEntity(TagDto tagDto);
}
