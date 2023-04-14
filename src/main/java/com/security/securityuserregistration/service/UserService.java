package com.security.securityuserregistration.service;

import com.security.securityuserregistration.dto.request.UserRequest;
import com.security.securityuserregistration.dto.response.UserResponse;

import java.util.UUID;

public interface UserService {

    UserResponse create(UserRequest userRequest);

    void validate(UserRequest userRequest);

    UserResponse update(UUID uuid, UserRequest userRequest);
}
