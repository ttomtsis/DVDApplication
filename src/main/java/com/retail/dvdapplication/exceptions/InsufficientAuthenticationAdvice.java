package com.retail.dvdapplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class InsufficientAuthenticationAdvice extends RuntimeException {

    @ResponseBody
    @ExceptionHandler(InsufficientAuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String InssuficientAuthenticationExceptionnHandler(InsufficientAuthenticationException ex) {
        return ex.getMessage();
    }
}
