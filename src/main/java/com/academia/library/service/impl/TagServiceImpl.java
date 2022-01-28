package com.academia.library.service.impl;

import com.academia.library.dto.TagRequestDto;
import com.academia.library.dto.TagResponseDto;
import com.academia.library.exception.TagAlreadyExistException;
import com.academia.library.exception.TagNotFoundException;
import com.academia.library.mapper.TagMapper;
import com.academia.library.model.Tag;
import com.academia.library.repository.TagRepository;
import com.academia.library.service.TagService;
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

    @Override
    @Transactional(readOnly = true)
    public TagResponseDto findById(Long id) {
        Tag tag = getTagOrThrowException(id);
        return tagMapper.toDto(tag);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagResponseDto> findAll() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream()
                .map(tagMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TagResponseDto create(TagRequestDto tagRequestDto) {
        if (tagRepository.existsByName(tagRequestDto.getName())) {
            throw new TagAlreadyExistException(tagRequestDto.getName());
        }

        Tag tag = tagMapper.toEntity(tagRequestDto);
        return tagMapper.toDto(tagRepository.save(tag));
    }

    @Override
    @Transactional
    public TagResponseDto update(Long id, TagRequestDto tagRequestDto) {
        if (tagRepository.existsByName(tagRequestDto.getName())) {
            throw new TagAlreadyExistException(tagRequestDto.getName());
        }

        Tag tag = getTagOrThrowException(id);
        tag = tagMapper.updateRequestToEntity(tagRequestDto, tag);
        Tag updateTag = tagRepository.save(tag);
        return tagMapper.toDto(updateTag);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Tag tag = getTagOrThrowException(id);
        tagRepository.delete(tag);
    }

    @Transactional(readOnly = true)
    public Tag getTagOrThrowException(Long id){
        return tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException(id));
    }
}
