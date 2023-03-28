package com.retail.dvdapplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class SQLIntegrityConstraintViolationAdvice {

    @ResponseBody
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String IntegrityViolationHandler(SQLIntegrityConstraintViolationException ex) {
        return ex.getMessage();
    }
}
