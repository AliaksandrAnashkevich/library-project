package com.academia.library.service.impl;

import com.academia.library.config.PasswordConfig;
import com.academia.library.dto.UserRequestDto;
import com.academia.library.dto.UserResponseDto;
import com.academia.library.mapper.UserMapper;
import com.academia.library.model.Role;
import com.academia.library.model.User;
import com.academia.library.repository.RoleRepository;
import com.academia.library.repository.UserRepository;
import com.academia.library.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class  UsersServiceImpl implements UserService {

    private final static String ROLE_USER_NAME = "ROLE_USER";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordConfig passwordConfig;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto add(UserRequestDto userRequestDto) {

        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new RuntimeException();
        }

        String bCryptPassword = passwordConfig.passwordEncoder().encode(userRequestDto.getPassword());
        User userBeforeAdd = userMapper.toEntity(userRequestDto);
        Role role = roleRepository.findByName(ROLE_USER_NAME);
        userBeforeAdd.setRoles(Set.of(role));
        userBeforeAdd.setPassword(bCryptPassword);
        userBeforeAdd.setCreateAt(LocalDateTime.now());
        userBeforeAdd.setUpdateAt(LocalDateTime.now());

        User userAfterAdd = userRepository.save(userBeforeAdd);
        log.info("Registration new user with username {}", userRequestDto.getEmail());
        return userMapper.toDto(userAfterAdd);
    }
}
