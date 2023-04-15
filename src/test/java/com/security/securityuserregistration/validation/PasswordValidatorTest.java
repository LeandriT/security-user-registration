package com.security.securityuserregistration.validation;

import com.security.securityuserregistration.validation.impl.PasswordValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({MockitoExtension.class})
class PasswordValidatorTest {
    @InjectMocks
    private PasswordValidatorImpl passwordValidator;

    @BeforeEach
    public void setUp() {
        org.springframework.test.util.ReflectionTestUtils.setField(passwordValidator, "PASSWORD_PATTERN", "^(?=.*[0-9])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{4,}");
    }

    @Test
    void testPasswordValid() {
        //GIVEN
        String password = "q4243234.,A2";
        //WHEN
        boolean isValid = passwordValidator.isValid(password);
        //THEN
        assertTrue(isValid);
    }

    @Test
    void testPasswordInValid() {
        //GIVEN
        String password = "ABC";
        //WHEN
        boolean isValid = passwordValidator.isValid(password);
        //THEN
        assertFalse(isValid);
    }

}