package com.retail.dvdapplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JakartaValidationConstraintViolationAdvice {

    @ResponseBody
    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String IntegrityViolationHandler(jakarta.validation.ConstraintViolationException ex) {
        return ex.getMessage();
    }
}
