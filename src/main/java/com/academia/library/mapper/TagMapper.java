package com.academia.library.mapper;

import com.academia.library.dto.TagRequest;
import com.academia.library.model.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {

    Tag toEntity(TagRequest tagDto);
}
