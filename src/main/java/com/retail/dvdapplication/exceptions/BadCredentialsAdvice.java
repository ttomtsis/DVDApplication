package com.retail.dvdapplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

// BadCredentialsException
public class BadCredentialsAdvice extends RuntimeException {
    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String BadCredentialsExceptionHandler(BadCredentialsException ex) {
        return ex.getMessage();
    }
}
