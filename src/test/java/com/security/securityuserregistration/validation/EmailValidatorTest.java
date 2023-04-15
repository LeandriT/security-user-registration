package com.security.securityuserregistration.validation;

import com.security.securityuserregistration.validation.impl.EmailValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({MockitoExtension.class})
class EmailValidatorTest {

    @InjectMocks
    private EmailValidatorImpl emailValidator;

    @BeforeEach
    public void setUp() {
        org.springframework.test.util.ReflectionTestUtils.setField(emailValidator, "EMAIL_PATTERN", "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b");
    }

    @Test
    void testValidEmail() {
        //ARRANGE
        String email = "domain@domain.cl";
        //ACT
        boolean isValid = emailValidator.isValid(email);
        //ASSERT
        assertTrue(isValid);
    }

    @Test
    void testInvalidEmail() {
        //ARRANGE
        String email = "223123";
        //ACT
        boolean isValid = emailValidator.isValid(email);
        //ASSERT
        assertFalse(isValid);
    }
}