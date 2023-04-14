package com.security.securityuserregistration.service;

import com.security.securityuserregistration.dto.request.UserRequest;

public interface JWTService {

    String createToken(UserRequest userRequest);
}
