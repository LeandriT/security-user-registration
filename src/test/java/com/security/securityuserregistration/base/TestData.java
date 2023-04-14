package com.security.securityuserregistration.base;

import com.github.javafaker.Faker;
import com.security.securityuserregistration.dto.request.PhoneRequest;
import com.security.securityuserregistration.dto.request.UserRequest;
import com.security.securityuserregistration.dto.response.UserResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public class TestData {
    private Faker faker;

    public TestData() {
        faker = new Faker();
    }

    public UserRequest userRequest() {
        return UserRequest.builder()
                .email(faker.internet().emailAddress())
                .name(faker.chuckNorris().fact())
                .password(faker.pokemon().name())
                .build();
    }

    public PhoneRequest phoneRequest() {
        return PhoneRequest.builder()
                .number(faker.phoneNumber().cellPhone())
                .cityCode(faker.random().nextInt(10, 20))
                .countryCode(faker.random().nextInt(100, 200))
                .build();
    }

    public UserResponse userResponse() {
        LocalDateTime now = LocalDateTime.now();
        return UserResponse.builder()
                .uuid(UUID.randomUUID())
                .createdAt(now)
                .updatedAt(LocalDateTime.now().plusMinutes(1))
                .lastLogin(now)
                .token(faker.crypto().sha512())
                .isActive(true)
                .build();
    }

}
