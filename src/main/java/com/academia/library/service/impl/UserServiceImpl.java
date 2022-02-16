package com.academia.library.service.impl;

import com.academia.library.cryptor.Cryptor;
import com.academia.library.dto.request.AuthRequest;
import com.academia.library.dto.response.AuthResponse;
import com.academia.library.dto.request.UserRequest;
import com.academia.library.dto.response.UserResponse;
import com.academia.library.exception.InvalidAuthRequestDataException;
import com.academia.library.exception.RoleNotFoundException;
import com.academia.library.mapper.UserMapper;
import com.academia.library.model.Role;
import com.academia.library.model.User;
import com.academia.library.repository.RoleRepository;
import com.academia.library.repository.UserRepository;
import com.academia.library.security.JwtTokenProvider;
import com.academia.library.security.JwtUser;
import com.academia.library.service.UserService;
import com.academia.library.util.RoleNames;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final Cryptor cryptor;

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        String email = cryptor.encode(authRequest.getEmail());
        String password = authRequest.getPassword();

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));
            JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
            String token = jwtTokenProvider.createToken(email, jwtUser.getRoles());
            String decodeEmail = cryptor.decode(email);
            return AuthResponse.builder()
                    .email(decodeEmail)
                    .token(token)
                    .build();
        } catch (AuthenticationException ex) {
            throw new InvalidAuthRequestDataException();
        }
    }

    @Override
    @Transactional
    public UserResponse create(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException();
        }
        Role role = roleRepository.findByName(RoleNames.USER_ROLE)
                .orElseThrow(() -> new RoleNotFoundException(RoleNames.USER_ROLE));
        String bCryptPassword = passwordEncoder.encode(userRequest.getPassword());

        User user = userMapper.toEntity(userRequest);
        user.setRoles(Set.of(role));
        user.setPassword(bCryptPassword);
        user.setCreateAt(LocalDateTime.now());
        user.setUpdateAt(LocalDateTime.now());
        encryptUserFields(user);

        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    private void encryptUserFields(User user) {
        user.setEmail(cryptor.encode(user.getEmail()));
        user.setFirstName(cryptor.encode(user.getFirstName()));
        user.setLastName(cryptor.encode(user.getLastName()));
    }
}