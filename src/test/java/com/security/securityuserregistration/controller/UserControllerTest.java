package com.security.securityuserregistration.controller;

import com.security.securityuserregistration.dto.request.PhoneRequest;
import com.security.securityuserregistration.dto.request.UserRequest;
import com.security.securityuserregistration.dto.response.UserResponse;
import com.security.securityuserregistration.service.UserService;
import com.security.securityuserregistration.base.TestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith({MockitoExtension.class})
class UserControllerTest {

    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;
    private TestData testData;

    @BeforeEach
    public void setUp() {
        testData = new TestData();
    }

    @Test
    void createUser() {
        UserRequest userRequest = testData.userRequest();
        List<PhoneRequest> phoneRequestList = new ArrayList<>() {{
            add(testData.phoneRequest());
            add(testData.phoneRequest());
        }};
        userRequest.setPhones(phoneRequestList);
        UserResponse userResponse = testData.userResponse();
        Mockito.when(userService.create(userRequest)).thenReturn(userResponse);

        ResponseEntity<UserResponse> response = userController.create(userRequest);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void updateUser() {
        UserRequest userRequest = testData.userRequest();
        UUID userUuid = UUID.randomUUID();
        List<PhoneRequest> phoneRequestList = new ArrayList<>() {{
            add(testData.phoneRequest());
            add(testData.phoneRequest());
        }};
        userRequest.setPhones(phoneRequestList);
        UserResponse userResponse = testData.userResponse();
        Mockito.when(userService.update(userUuid, userRequest)).thenReturn(userResponse);

        ResponseEntity<UserResponse> response = userController.update(userUuid, userRequest);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Mockito.verify(userService).update(userUuid, userRequest);
    }
}