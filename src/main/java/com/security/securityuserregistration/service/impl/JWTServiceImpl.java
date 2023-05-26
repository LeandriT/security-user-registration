package com.security.securityuserregistration.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.security.securityuserregistration.dto.request.UserRequest;
import com.security.securityuserregistration.exception.GenericException;
import com.security.securityuserregistration.service.JWTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Service
public class JWTServiceImpl implements JWTService {

    @Value("${app.config.jwt-sign}")
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
    @Override
    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        com.auth0.jwt.interfaces.JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            decodedJWT.getClaims();
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            throw new GenericException("Tiempo de token expirado.");
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            throw new GenericException("Verificacion token fallido");
        }
        return verifier.verify(token).getSubject();
    }
}
