package com.academia.library.mapper;

import com.academia.library.dto.request.TagRequest;
import com.academia.library.dto.response.TagResponse;
import com.academia.library.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagResponse toDto(Tag tag);

    Tag toEntity(TagRequest tagRequest);

    Tag updateRequestToEntity(TagRequest tagRequest, @MappingTarget Tag tag);
}
