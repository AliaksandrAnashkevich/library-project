package com.academia.library.mapper;

import com.academia.library.dto.UserDto;
import com.academia.library.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDto userDto);
}
