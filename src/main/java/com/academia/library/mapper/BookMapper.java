package com.academia.library.mapper;

import com.academia.library.dto.BookRequestDto;
import com.academia.library.dto.BookResponseDto;
import com.academia.library.model.Author;
import com.academia.library.model.Book;
import com.academia.library.model.Tag;
import com.academia.library.repository.AuthorRepository;
import com.academia.library.repository.TagRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        imports = {LocalDateTime.class})
public abstract class BookMapper {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TagRepository tagRepository;

    @AfterMapping
    void setTime(@MappingTarget Book book, BookRequestDto bookRequestDto) {
        Author author = authorRepository.getById(bookRequestDto.getAuthorId());
        book.setAuthor(author);

        Set<Tag> tags = bookRequestDto.getTagsId().stream()
                .map(id -> tagRepository.getById(id))
                .collect(Collectors.toSet());
        book.setTags(tags);
    }

    public abstract BookResponseDto toDto(Book book);

    @Mapping(expression = "java(LocalDateTime.now())", target = "createAt")
    @Mapping(expression = "java(LocalDateTime.now())", target = "updateAt")
    public abstract Book toEntity(BookRequestDto bookRequestDto);

    @Mapping(expression = "java(LocalDateTime.now())", target = "updateAt")
    public abstract Book mapRequestToEntity(BookRequestDto bookRequestDto, @MappingTarget Book book);


}
