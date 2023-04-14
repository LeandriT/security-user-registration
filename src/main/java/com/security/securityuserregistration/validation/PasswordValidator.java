package com.security.securityuserregistration.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

    //private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[0-9].*[0-9])(?=.*[a-z])(?!.*[^a-zA-Z0-9]).{4,}$";
    //private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    //private static final String PASSWORD_PATTERN = "(?=.*[a-z]).+";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{4,}";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValid(final String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
