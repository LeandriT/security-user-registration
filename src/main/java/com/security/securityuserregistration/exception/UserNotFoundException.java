package com.security.securityuserregistration.exception;

import java.text.MessageFormat;
import java.util.UUID;

public class UserNotFoundException extends RuntimeException{
	
	public UserNotFoundException(final UUID id){
        super(MessageFormat.format("Could not find user with id: {0}", id));
    }
	
}
