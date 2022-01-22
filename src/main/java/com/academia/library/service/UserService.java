package com.academia.library.service;

import com.academia.library.dto.UserDto;

public interface UserService {

    boolean existByEmail(String email);

    void save(UserDto userDto);
}
