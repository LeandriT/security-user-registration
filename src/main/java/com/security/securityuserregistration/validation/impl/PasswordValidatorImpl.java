package com.security.securityuserregistration.validation.impl;

import com.security.securityuserregistration.validation.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class PasswordValidatorImpl implements PasswordValidator {

    //private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[0-9].*[0-9])(?=.*[a-z])(?!.*[^a-zA-Z0-9]).{4,}$";
    //private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    //private static final String PASSWORD_PATTERN = "(?=.*[a-z]).+";
    //private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{4,}";
    @Value("${app.config.password-pattern}")
    private String PASSWORD_PATTERN;

    @Override
    public boolean isValid(final String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
