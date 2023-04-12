package com.retail.dvdapplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class ExceptionAdvice extends RuntimeException{

    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String BadCredentialsExceptionHandler(BadCredentialsException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(DVDNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String DVDNotFoundHandler(DVDNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InsufficientAuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String InssuficientAuthenticationExceptionnHandler(InsufficientAuthenticationException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String IntegrityViolationHandler(jakarta.validation.ConstraintViolationException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String IntegrityViolationHandler(SQLIntegrityConstraintViolationException ex) {
        return ex.getMessage();
    }

}



