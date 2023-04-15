package com.security.securityuserregistration.exception;

public class EmailIsAlreadyRegisteredException extends RuntimeException {

    public EmailIsAlreadyRegisteredException(String message) {
        super(message);
    }

}
