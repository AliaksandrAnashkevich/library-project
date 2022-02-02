package com.academia.library.mapper;

import com.academia.library.dto.BookRequest;
import com.academia.library.dto.BookResponse;
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

    public abstract BookResponse toDto(Book book);

    @Mapping(expression = "java(LocalDateTime.now())", target = "createAt")
    @Mapping(expression = "java(LocalDateTime.now())", target = "updateAt")
    public abstract Book toEntity(BookRequest bookRequest);

    @Mapping(expression = "java(LocalDateTime.now())", target = "updateAt")
    public abstract Book mapRequestToEntity(BookRequest bookRequest, @MappingTarget Book book);

    @AfterMapping
    void setAuthorAndTagsById(@MappingTarget Book book, BookRequest bookRequest) {
        Author author = authorRepository.getById(bookRequest.getAuthorId());
        book.setAuthor(author);

        Set<Tag> tags = bookRequest.getTagsId().stream()
                .map(id -> tagRepository.getById(id))
                .collect(Collectors.toSet());
        book.setTags(tags);
    }

}
