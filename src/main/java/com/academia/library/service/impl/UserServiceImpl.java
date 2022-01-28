package com.academia.library.service.impl;

import com.academia.library.cryptor.UserCryptor;
import com.academia.library.dto.AuthRequestDto;
import com.academia.library.dto.AuthResponseDto;
import com.academia.library.dto.UserRequestDto;
import com.academia.library.dto.UserResponseDto;
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
    private final UserCryptor userCryptor;

    @Override
    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        String email = userCryptor.encode(authRequestDto.getEmail());
        String password = authRequestDto.getPassword();

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));
            JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
            String token = jwtTokenProvider.createToken(email, jwtUser.getRoles());
            String decodeEmail = userCryptor.decode(email);
            return AuthResponseDto.builder()
                    .email(decodeEmail)
                    .token(token)
                    .build();
        } catch (AuthenticationException ex) {
            throw new InvalidAuthRequestDataException();
        }
    }

    @Override
    public UserResponseDto create(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new RuntimeException();
        }
        Role role = roleRepository.findByName(RoleNames.USER_ROLE)
                .orElseThrow(() -> new RoleNotFoundException(RoleNames.USER_ROLE));
        String bCryptPassword = passwordEncoder.encode(userRequestDto.getPassword());

        User user = userMapper.toEntity(userRequestDto);
        user.setRoles(Set.of(role));
        user.setPassword(bCryptPassword);
        user.setCreateAt(LocalDateTime.now());
        user.setUpdateAt(LocalDateTime.now());
        encryptUserFields(user);

        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    private void encryptUserFields(User user) {
        user.setEmail(userCryptor.encode(user.getEmail()));
        user.setFirstName(userCryptor.encode(user.getFirstName()));
        user.setLastName(userCryptor.encode(user.getLastName()));
    }
}
