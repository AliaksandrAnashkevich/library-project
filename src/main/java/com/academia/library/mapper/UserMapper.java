package com.academia.library.mapper;

import com.academia.library.dto.UserRequestDto;
import com.academia.library.dto.UserResponseDto;
import com.academia.library.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toDto(User user);

    User toEntity(UserRequestDto userDto);
}
