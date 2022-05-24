package com.academia.library.service.impl;

import com.academia.library.dto.request.TagRequest;
import com.academia.library.dto.response.TagResponse;
import com.academia.library.exception.TagNotFoundException;
import com.academia.library.mapper.TagMapper;
import com.academia.library.model.Tag;
import com.academia.library.repository.TagRepository;
import com.academia.library.service.TagService;
import com.academia.library.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final TagValidator tagValidator;

    @Override
    @Transactional(readOnly = true)
    public TagResponse findById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException(id));

        return tagMapper.toDto(tag);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagResponse> findAll() {
        List<Tag> tags = tagRepository.findAll();

        return tags.stream()
                .map(tagMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TagResponse create(TagRequest tagRequest) {
        tagValidator.validate(tagRequest);

        Tag tag = tagMapper.toEntity(tagRequest);
        Tag saveTag = tagRepository.save(tag);

        return tagMapper.toDto(saveTag);
    }

    @Override
    @Transactional
    public TagResponse update(Long id, TagRequest tagRequest) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException(id));

        tagValidator.validate(tagRequest);

        tag = tagMapper.updateRequestToEntity(tagRequest, tag);
        Tag updateTag = tagRepository.save(tag);

        return tagMapper.toDto(updateTag);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException(id));

        tagRepository.delete(tag);
    }
}
