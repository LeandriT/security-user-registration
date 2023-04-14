package com.security.securityuserregistration.exception;

public class EmailIsAlreadyAssignedException extends RuntimeException{

	public EmailIsAlreadyAssignedException(String message){
        super(message);
    }

}
