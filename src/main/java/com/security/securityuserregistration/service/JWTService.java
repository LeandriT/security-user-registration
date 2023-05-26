package com.security.securityuserregistration.service;

import com.security.securityuserregistration.dto.request.UserRequest;
import org.springframework.util.Base64Utils;

public interface JWTService {
    String SECRET = Base64Utils.encodeToString("96142f5a-157a-41ed-aa36-8d049acec28c".getBytes());
    long EXPIRATION_DATE = 28_800_000;//8 hours

    String createToken(UserRequest userRequest);

    String validateToken(String token);
}
