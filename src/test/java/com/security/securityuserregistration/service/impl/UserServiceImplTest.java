package com.security.securityuserregistration.service.impl;

import com.security.securityuserregistration.base.TestData;
import com.security.securityuserregistration.dto.request.PhoneRequest;
import com.security.securityuserregistration.dto.request.UserRequest;
import com.security.securityuserregistration.dto.response.UserResponse;
import com.security.securityuserregistration.exception.*;
import com.security.securityuserregistration.mapper.UserMapper;
import com.security.securityuserregistration.model.Phone;
import com.security.securityuserregistration.model.User;
import com.security.securityuserregistration.repository.UserRepository;
import com.security.securityuserregistration.service.JWTService;
import com.security.securityuserregistration.validation.impl.EmailValidatorImpl;
import com.security.securityuserregistration.validation.impl.PasswordValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowableOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class UserServiceImplTest {
    @InjectMocks
    @Spy
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Spy
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JWTService jwtService;
    private TestData testData;

    @Mock
    private EmailValidatorImpl emailValidatorImpl;
    @Mock
    private PasswordValidatorImpl passwordValidatorImpl;

    @BeforeEach
    public void setUp() {
        testData = new TestData();
        org.springframework.test.util.ReflectionTestUtils.setField(emailValidatorImpl, "EMAIL_PATTERN", "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b");
        org.springframework.test.util.ReflectionTestUtils.setField(passwordValidatorImpl, "PASSWORD_PATTERN", "^(?=.*[0-9])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{4,}");
    }

    @Nested
    @DisplayName("Test create user")
    class CreateUser {
        @Test
        void createUserThenSuccess() {
            UserRequest userRequest = testData.userRequest();
            List<PhoneRequest> phoneRequestList = new ArrayList<>() {{
                add(testData.phoneRequest());
                add(testData.phoneRequest());
            }};
            userRequest.setPhones(phoneRequestList);
            User user = User.builder()
                    .name(userRequest.getName())
                    .email(userRequest.getEmail())
                    .password(userRequest.getPassword())
                    .phones(userRequest.getPhones().stream().map(this::buildPhone).collect(Collectors.toList()))
                    .build();
            UserResponse userResponse = UserResponse.builder()
                    .createdAt(LocalDateTime.now())
                    .isActive(true)
                    .token(UUID.randomUUID().toString())
                    .build();
            Mockito.doNothing().when(userService).validate(userRequest);
            when(userMapper.toEntity(userRequest)).thenReturn(user);
            when(passwordEncoder.encode(any())).thenReturn(new String());
            when(jwtService.createToken(userRequest)).thenReturn(new String());
            when(userRepository.save(user)).thenReturn(user);
            when(userMapper.toDto(any())).thenReturn(userResponse);

            UserResponse response = userService.create(userRequest);

            verify(userMapper).toEntity(userRequest);
            verify(passwordEncoder).encode(any());
            verify(jwtService).createToken(userRequest);
            verify(userRepository).save(user);
            assertThat(response).isNotNull();
            assertThat(response.isActive()).isTrue();
            assertThat(response.getToken()).isNotNull();
        }

        @Test
        void createUserThenThrowError() {
            UserRequest userRequest = testData.userRequest();
            List<PhoneRequest> phoneRequestList = new ArrayList<>() {{
                add(testData.phoneRequest());
                add(testData.phoneRequest());
            }};
            userRequest.setPhones(phoneRequestList);
            User user = User.builder()
                    .name(userRequest.getName())
                    .email(userRequest.getEmail())
                    .password(userRequest.getPassword())
                    .phones(userRequest.getPhones().stream().map(this::buildPhone).collect(Collectors.toList()))
                    .build();
            Mockito.doNothing().when(userService).validate(userRequest);
            when(userMapper.toEntity(userRequest)).thenReturn(user);
            when(passwordEncoder.encode(any())).thenReturn("");
            when(jwtService.createToken(userRequest)).thenReturn("");
            doThrow(new DataIntegrityViolationException("")).when(userRepository).save(any());

            assertThrows(GenericException.class, () -> userService.create(userRequest));

            verify(userMapper).toEntity(userRequest);
            verify(passwordEncoder).encode(any());
            verify(jwtService).createToken(userRequest);
            verify(userRepository, times(1)).save(user);
        }

        private Phone buildPhone(PhoneRequest phoneRequest) {
            return Phone.builder()
                    .number(phoneRequest.getNumber())
                    .countryCode(phoneRequest.getCountryCode())
                    .cityCode(phoneRequest.getCityCode())
                    .build();
        }
    }

    @Nested
    @DisplayName("Test validate user request")
    class validateUserRequest {
        @Test
        void validateEmailAlreadyRegistered() {
            UserRequest userRequest = testData.userRequest();
            when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(true);

            EmailIsAlreadyRegisteredException throwable = catchThrowableOfType(() ->
                    userService.validate(userRequest), EmailIsAlreadyRegisteredException.class);

            assertEquals("El correo " + userRequest.getEmail() + " ya esta registrado", throwable.getMessage());
        }

        @Test
        void validateEmailIsIncorrect() {
            UserRequest userRequest = testData.userRequest();
            userRequest.setEmail("asd");
            when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(false);

            EmailValidationException throwable = catchThrowableOfType(() ->
                    userService.validate(userRequest), EmailValidationException.class);

            assertEquals("Email is not valid", throwable.getMessage());
        }

        @Test
        void validateIsStrongPasswordError() {
            UserRequest userRequest = testData.userRequest();
            userRequest.setEmail("asd@asd.com");
            userRequest.setPassword("asd");
            when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(false);
            when(emailValidatorImpl.isValid(anyString())).thenReturn(true);
            when(passwordValidatorImpl.isValid(anyString())).thenReturn(false);

            PasswordValidationException throwable = catchThrowableOfType(() ->
                    userService.validate(userRequest), PasswordValidationException.class);

            assertEquals("Password is not strong", throwable.getMessage());
        }

        @Test
        void validateIsStrongPasswordCorrect() {
            UserRequest userRequest = testData.userRequest();
            userRequest.setPassword("g15.,A939");
            when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(false);
            when(emailValidatorImpl.isValid(anyString())).thenReturn(true);
            when(passwordValidatorImpl.isValid(anyString())).thenReturn(true);

            userService.validate(userRequest);
        }
    }

    @Nested
    @DisplayName("Test update user")
    class UpdateUser {
        @Test
        void updateUserNotFoundError() {
            String token="token";
            UserRequest userRequest = testData.userRequest();
            UUID userUuid = UUID.randomUUID();
            when(userRepository.findById(userUuid)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> userService.update(userUuid, userRequest, token));

            verify(userRepository).findById(userUuid);
        }

        @Test
        void updateUserFound() {
            String token = "token";
            UserRequest userRequest = testData.userRequest();
            UUID userUuid = UUID.randomUUID();
            User user = User.builder()
                    .name(userRequest.getName())
                    .email(userRequest.getEmail())
                    .password(userRequest.getPassword())
                    .build();
            user.setUuid(userUuid);
            UserResponse userResponse = UserResponse.builder()
                    .createdAt(LocalDateTime.now())
                    .isActive(true)
                    .token(UUID.randomUUID().toString())
                    .build();
            userResponse.setUuid(userUuid);
            when(userRepository.findById(userUuid)).thenReturn(Optional.of(user));
            when(userMapper.updateModel(userRequest, user)).thenReturn(user);
            when(userRepository.save(user)).thenReturn(user);
            when(userMapper.toDto(user)).thenReturn(userResponse);
            when(jwtService.validateToken(any())).thenReturn("token");

            UserResponse update = userService.update(userUuid, userRequest, token);

            verify(userRepository).findById(userUuid);
            verify(userMapper).updateModel(userRequest, user);
            verify(userRepository).save(user);
            verify(jwtService).validateToken(anyString());
            assertThat(update).isNotNull();
            assertThat(update.isActive()).isTrue();
        }
    }

    @Nested
    @DisplayName("Test show user")
    class ShowUser {
        @Test
        void userNotFound() {
            UUID userUuid = UUID.randomUUID();
            when(userRepository.findById(userUuid)).thenReturn(Optional.empty());
            assertThrows(UserNotFoundException.class, () -> userService.show(userUuid));
            verify(userRepository).findById(userUuid);
        }

        @Test
        void testUserFound() {
            UUID userUuid = UUID.randomUUID();
            User user = User.builder()
                    .name("Frank")
                    .token(UUID.randomUUID().toString())
                    .password("D2fdAbE5e9bcAFDBE5bEeED3b795ecf44B35e99B6D28591429fEE6B7C0BcCed1")
                    .additionalToken("additionaltoken")
                    .build();
            user.setUuid(userUuid);
            UserResponse userResponse = testData.userResponse();
            userResponse.setUuid(userUuid);
            userResponse.setName(user.getName());
            userResponse.setToken(user.getToken());
            userResponse.setAdditionalToken(user.getAdditionalToken());
            when(userRepository.findById(userUuid)).thenReturn(Optional.of(user));
            when(userMapper.toDto(user)).thenReturn(userResponse);

            UserResponse userResponseResult = userService.show(userUuid);

            verify(userRepository).findById(userUuid);
            verify(userMapper).toDto(user);
            assertThat(userResponseResult).isNotNull();
            assertThat(userResponseResult.getUuid()).isEqualTo(userUuid);
            assertThat(userResponseResult.getName()).isEqualTo(userResponseResult.getName());
        }
    }

    @Nested
    @DisplayName("Test paginate user")
    class PaginateUser {
        @Test
        void testPagination() {
            UUID userUuid = UUID.randomUUID();
            User user = User.builder()
                    .name("Frank")
                    .token(UUID.randomUUID().toString())
                    .password("D2fdAbE5e9bcAFDBE5bEeED3b795ecf44B35e99B6D28591429fEE6B7C0BcCed1")
                    .additionalToken("additionaltoken")
                    .build();
            user.setUuid(userUuid);

            UserResponse userResponse = testData.userResponse();
            userResponse.setUuid(userUuid);
            userResponse.setName(user.getName());
            userResponse.setToken(user.getToken());
            userResponse.setAdditionalToken(user.getAdditionalToken());
            List<User> userResponseList = new ArrayList() {{
                {
                    add(user);
                }
            }};
            Pageable pageable = PageRequest.of(0, 10);
            Page<User> userPaginated = new PageImpl<User>(userResponseList, pageable, 1L);
            when(userRepository.findAll(pageable)).thenReturn(userPaginated);
            when(userMapper.toDto(user)).thenReturn(userResponse);

            Page<UserResponse> userResponsePage = userService.index(pageable);

            verify(userRepository).findAll(pageable);
            verify(userMapper).toDto(user);
            assertThat(userResponsePage.getTotalElements()).isEqualTo(1);
            assertThat(userResponsePage.getContent()).hasSize(1);
            userResponsePage.getContent().forEach(item -> {
                        assertThat(item).isNotNull();
                        assertThat(item.getUuid()).isEqualTo(userUuid);
                        assertThat(item.getName()).isNotNull();
                    }
            );
        }
    }

}