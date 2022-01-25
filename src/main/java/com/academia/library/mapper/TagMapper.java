package com.academia.library.mapper;

import com.academia.library.dto.TagRequestDto;
import com.academia.library.dto.TagResponseDto;
import com.academia.library.model.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {

    Tag toEntity(TagRequestDto tagDto);
}
