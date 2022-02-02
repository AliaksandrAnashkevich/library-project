package com.academia.library.service;

import com.academia.library.dto.TagRequest;
import com.academia.library.dto.TagResponse;
import com.academia.library.model.Tag;

import java.util.List;

public interface TagService {

    TagResponse findById(Long id);

    List<TagResponse> findAll();

    TagResponse create(TagRequest tagRequestDto);

    TagResponse update(Long id, TagRequest tagRequestDto);

    void delete(Long id);
}
