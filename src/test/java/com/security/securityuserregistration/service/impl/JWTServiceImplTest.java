package com.security.securityuserregistration.service.impl;

import com.security.securityuserregistration.base.TestData;
import com.security.securityuserregistration.dto.request.UserRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
class JWTServiceImplTest {
    @InjectMocks
    private JWTServiceImpl jwtService;
    private TestData testData;

    @BeforeEach
    public void setUp() {
        testData = new TestData();
        org.springframework.test.util.ReflectionTestUtils.setField(jwtService, "sign", "12345");
    }

    @Test
    void createToken() {
        UserRequest userRequest = testData.userRequest();

        String token = jwtService.createToken(userRequest);

        Assertions.assertThat(token).isNotNull();
    }
}