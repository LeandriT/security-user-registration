package com.security.securityuserregistration.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.security.securityuserregistration.dto.request.UserRequest;
import com.security.securityuserregistration.service.JWTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Service
public class JWTServiceImpl implements JWTService {
    long EXPIRATION_DATE = 28_800_000;//8 hours
    @Value("${spring.security.jwt.sign}")
    private String sign;

    @Override
    public String createToken(UserRequest userRequest) {
        Map<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("roles", new HashSet());
        return JWT.create()
                .withHeader(headerClaims)
                .withClaim("name", userRequest.getName())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_DATE))
                .sign(Algorithm.HMAC256(sign));
    }
}
