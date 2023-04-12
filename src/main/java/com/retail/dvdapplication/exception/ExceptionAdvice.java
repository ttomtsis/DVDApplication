package com.retail.dvdapplication.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class ExceptionAdvice {

    private static Logger log = LoggerFactory.getLogger("Exception Handler");


/*    @ResponseBody
    @ExceptionHandler(InsufficientAuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String InssuficientAuthenticationHandler(InsufficientAuthenticationException ex) {
        log.info("Insufficient Authentication");
        return "You need to be authenticated to access this resourced";
    }

    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ResponseEntity<Object> BadCredentialsHandler(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("pls work :')");
    }
    @ResponseBody
    @ExceptionHandler(MissingCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String MissingCredentialsHandler(MissingCredentialsException ex) {
        log.info("Insufficient Authentication");
        return "Missing required information: " + ex.getMessage();
    }*/

    @ResponseBody
    @ExceptionHandler(DVDNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String DVDNotFoundHandler(DVDNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String SQLExceptionHandler(SQLException ex) {
        if (ex.getErrorCode()==23505) {
            return "The Requsted Object cannot be created because the provided name already exists";
        }
        return "Generic SQLException";
    }

    // Thrown when invalid genre ( enum ) is provided
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String HttpMessageNotReadableHandler(HttpMessageNotReadableException ex) {
        if (ex.getMessage().contains("JSON parse error: No enum constant")) {
            return "The genre you provided is invalid";
        }
        return "Generic HttpMessageNotReadableException";
    }

}




