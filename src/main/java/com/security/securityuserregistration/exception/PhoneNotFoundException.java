package com.security.securityuserregistration.exception;

import java.text.MessageFormat;

public class PhoneNotFoundException extends RuntimeException{
	
	public PhoneNotFoundException(final Long id){
        super(MessageFormat.format("Could not find phone with id: {0}", id));
    }
	
}
