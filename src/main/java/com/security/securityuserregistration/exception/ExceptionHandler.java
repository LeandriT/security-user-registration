package com.security.securityuserregistration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(value = PasswordValidationException.class)
    public ResponseEntity<ErrorMessage> handlePasswordValidationException(PasswordValidationException ex){
		ErrorMessage errorResponse = new ErrorMessage();
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
	
	@org.springframework.web.bind.annotation.ExceptionHandler(value = EmailValidationException.class)
    public ResponseEntity<ErrorMessage> handleEmailValidationException(EmailValidationException ex){
		ErrorMessage errorResponse = new ErrorMessage();
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
	
	@org.springframework.web.bind.annotation.ExceptionHandler(value = EmailIsAlreadyAssignedException.class)
    public ResponseEntity<ErrorMessage> handleEmailIsAlreadyAssignedException(EmailIsAlreadyAssignedException ex){
		ErrorMessage errorResponse = new ErrorMessage();
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }
	
	@org.springframework.web.bind.annotation.ExceptionHandler(value = GenericException.class)
    public ResponseEntity<ErrorMessage> handleGenericException(GenericException ex){
		ErrorMessage errorResponse = new ErrorMessage();
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
