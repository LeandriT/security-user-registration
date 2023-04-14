package com.security.securityuserregistration.service.impl;

import com.security.securityuserregistration.dto.request.PhoneRequest;
import com.security.securityuserregistration.dto.request.UserRequest;
import com.security.securityuserregistration.dto.response.UserResponse;
import com.security.securityuserregistration.exception.*;
import com.security.securityuserregistration.mapper.UserMapper;
import com.security.securityuserregistration.model.Phone;
import com.security.securityuserregistration.model.User;
import com.security.securityuserregistration.repository.UserRepository;
import com.security.securityuserregistration.service.JWTService;
import com.security.securityuserregistration.service.UserService;
import com.security.securityuserregistration.validation.EmailValidator;
import com.security.securityuserregistration.validation.PasswordValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    public UserResponse create(UserRequest userRequest) {
        this.validate(userRequest);
        User user = userMapper.toEntity(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setLastLogin(LocalDateTime.now());
        user.setToken(UUID.randomUUID().toString());
        user.setAdditionalToken(jwtService.createToken(userRequest));
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new GenericException(ex.getMessage());
        }
        return userMapper.toDto(user);
    }

    @Override
    public void validate(UserRequest userRequest) {
        boolean existUserWithEmail = userRepository.existsByEmail(userRequest.getEmail());
        if (existUserWithEmail) {
            String message = String.format("El correo %s ya esta registrado", userRequest.getEmail());
            throw new EmailIsAlreadyAssignedException(message);
        }
        boolean isValidEmail = EmailValidator.isValid(userRequest.getEmail());
        if (!isValidEmail) {
            throw new EmailValidationException("Email is not valid");
        }
        boolean isStrongPassword = PasswordValidator.isValid(userRequest.getPassword());
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
}
