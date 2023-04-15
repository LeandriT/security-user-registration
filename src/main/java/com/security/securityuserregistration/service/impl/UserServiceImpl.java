package com.security.securityuserregistration.service.impl;

import com.security.securityuserregistration.dto.request.UserRequest;
import com.security.securityuserregistration.dto.response.UserResponse;
import com.security.securityuserregistration.exception.*;
import com.security.securityuserregistration.mapper.UserMapper;
import com.security.securityuserregistration.model.User;
import com.security.securityuserregistration.repository.UserRepository;
import com.security.securityuserregistration.service.JWTService;
import com.security.securityuserregistration.service.UserService;
import com.security.securityuserregistration.validation.impl.EmailValidatorImpl;
import com.security.securityuserregistration.validation.impl.PasswordValidatorImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    private final EmailValidatorImpl emailValidatorImpl;
    private final PasswordValidatorImpl passwordValidatorImpl;

    @Override
    public UserResponse create(UserRequest userRequest) {
        this.validate(userRequest);
        String jwtToken = jwtService.createToken(userRequest);
        User user = userMapper.toEntity(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setToken(jwtToken);
        user.setAdditionalToken(UUID.randomUUID().toString());
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            log.error("Error saving user: {}", ex.getMessage());
            throw new GenericException("Ocurrió un error al guardar el usuario.");
        }
        return userMapper.toDto(user);
    }

    @Override
    public void validate(UserRequest userRequest) {
        boolean existUserWithEmail = userRepository.existsByEmail(userRequest.getEmail());
        if (existUserWithEmail) {
            String message = String.format("El correo %s ya esta registrado", userRequest.getEmail());
            throw new EmailIsAlreadyRegisteredException(message);
        }
        boolean isValidEmail = emailValidatorImpl.isValid(userRequest.getEmail());
        if (!isValidEmail) {
            throw new EmailValidationException("Email is not valid");
        }
        boolean isStrongPassword = passwordValidatorImpl.isValid(userRequest.getPassword());
        if (!isStrongPassword) {
            throw new PasswordValidationException("Password is not strong");
        }
    }

    @Override
    public UserResponse update(UUID uuid, UserRequest userRequest) {
        User user = userRepository.findById(uuid).orElseThrow(() -> new UserNotFoundException(uuid));
        user = userMapper.updateModel(userRequest, user);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserResponse show(UUID uuid) {
        User user = userRepository.findById(uuid).orElseThrow(() -> new UserNotFoundException(uuid));
        return userMapper.toDto(user);
    }

    @Override
    public Page<UserResponse> index(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return new PageImpl<UserResponse>(
                userPage.getContent().stream().map(userMapper::toDto).collect(Collectors.toList()),
                pageable,
                userPage.getTotalElements()
        );
    }
}
