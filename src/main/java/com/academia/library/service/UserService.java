package com.academia.library.service;

import com.academia.library.dto.request.AuthRequest;
import com.academia.library.dto.responce.AuthResponse;
import com.academia.library.dto.request.UserRequest;
import com.academia.library.dto.responce.UserResponse;

public interface UserService {

    AuthResponse login(AuthRequest authRequest);

    UserResponse create(UserRequest userRequest);
}