package com.academia.library.service.impl;

import com.academia.library.dto.UserDto;
import com.academia.library.mapper.UserMapper;
import com.academia.library.model.Role;
import com.academia.library.model.User;
import com.academia.library.repository.RoleRepository;
import com.academia.library.repository.UserRepository;
import com.academia.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void save(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        Role role = roleRepository.findByName("ROLE_USER");
        user.getRoles().add(role);
        user.setCreateAt(LocalDateTime.now());
        user.setUpdateAt(LocalDateTime.now());
        userRepository.save(user);
    }
}
