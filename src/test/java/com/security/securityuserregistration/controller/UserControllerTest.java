package com.security.securityuserregistration.controller;

import com.security.securityuserregistration.base.TestData;
import com.security.securityuserregistration.dto.request.PhoneRequest;
import com.security.securityuserregistration.dto.request.UserRequest;
import com.security.securityuserregistration.dto.response.UserResponse;
import com.security.securityuserregistration.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        when(userService.create(userRequest)).thenReturn(userResponse);

        ResponseEntity<UserResponse> response = userController.create(userRequest);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void updateUser() {
        UserRequest userRequest = testData.userRequest();
        String token="token";
        UUID userUuid = UUID.randomUUID();
        List<PhoneRequest> phoneRequestList = new ArrayList<>() {{
            add(testData.phoneRequest());
            add(testData.phoneRequest());
        }};
        userRequest.setPhones(phoneRequestList);
        UserResponse userResponse = testData.userResponse();
        when(userService.update(userUuid, userRequest, token)).thenReturn(userResponse);

        ResponseEntity<UserResponse> response = userController.update(userUuid, userRequest, token);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Mockito.verify(userService).update(userUuid, userRequest, token);
    }

    @Test
    void testShowUserRequest() {
        UUID userUuid = UUID.randomUUID();
        UserResponse userResponse = testData.userResponse();
        userResponse.setUuid(userUuid);
        when(userService.show(userUuid)).thenReturn(userResponse);

        ResponseEntity<UserResponse> response = userController.show(userUuid);

        verify(userService).show(userUuid);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Mockito.verify(userService).show(userUuid);
    }

    @Test
    void userPaginated() {
        UserResponse userResponse = testData.userResponse();
        Pageable pageable = PageRequest.of(0, 10);
        List<UserResponse> userResponseList = new ArrayList() {{
            {
                add(userResponse);
            }
        }};
        Page<UserResponse> userPaginated = new PageImpl<UserResponse>(userResponseList, pageable, 1L);
        when(userService.index(pageable)).thenReturn(userPaginated);

        ResponseEntity<Page<UserResponse>> responses = userController.index(pageable);

        verify(userService).index(pageable);
        assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<UserResponse> userResponseListResult = responses.getBody().toList();
        assertThat(userResponseListResult.size()).isEqualTo(1);
        userResponseListResult.forEach(item -> {
            assertThat(item).isNotNull();
            assertThat(item.getUuid()).isEqualTo(userResponse.getUuid());
            assertThat(item.getName()).isEqualTo(userResponse.getName());
        });
    }
}