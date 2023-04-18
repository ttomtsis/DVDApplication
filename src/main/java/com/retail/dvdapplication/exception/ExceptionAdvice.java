package com.retail.dvdapplication.exception;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.URI;
import java.sql.SQLException;

/*
* Exception advisor class, contains advice about how the controller
* should handle the exceptions thrown. NOTE: Currently the return type
* of most exceptions is "String", since it makes it easier for testing
* with Postman. Will be later replaced with "ResponseEntity"
*/
@RestControllerAdvice
public class ExceptionAdvice {

    private static Logger log = LoggerFactory.getLogger("Exception Handler");

// Related to issue #5 on github: https://github.com/ttomtsis/DVDApplication/issues/5
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

    // Thrown when a DVD is not found
    @ResponseBody
    @ExceptionHandler(DVDNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String DVDNotFoundHandler(DVDNotFoundException ex) {
        return ex.getMessage();
    }

    // General Exception Handler regarding SQL Errors
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
            return "The genre you provided is invalid ( ERROR CODE : 3 )";
        }
        if (ex.getMessage().contains("Required request body is missing")) {
            return "Missing required data, invalid request";
        }
        log.error(ex.getMessage());
        return "Function cannot be performed, there are syntax errors in your request's body";
    }

    // Thrown when parameters in the url provided by the user are invalid
    @ResponseBody
    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String MissingPathVariableHandler(MissingPathVariableException ex) {
        return "The data you provided was invalid, please try again ( ERROR CODE: 4 )";
    }

    // Thrown when user has provided a different type of variable than the one specified
    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String MethodArgumentTypeMismatchHandler(MethodArgumentTypeMismatchException ex) {
        return "The data you provided was invalid, please try again ( ERROR CODE: 5 )";
    }

    // Thrown by the DVDController when user has not provided a search query or other data
    @ResponseBody
    @ExceptionHandler(MissingRequiredDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String EmptyPathVariableHandler(MissingRequiredDataException ex) {
        return "You have not provided the required data for this function";
    }

    // Thrown when the DVD Reserves are below zero
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String ConstraintViolationHandler(ConstraintViolationException ex) {
        return "DVD Reserves cannot be less than zero";
    }

    // Thrown when client uses invalid HTTP method on existing Endpoint
    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.SEE_OTHER)
    ResponseEntity<Object> HttpRequestMethodNotSupportedHandler(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .location(URI.create("/api/dvds"))
                .build();
    }

    // Thrown when client tries to access an endpoint that does not exist
    @ResponseBody
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.SEE_OTHER)
    ResponseEntity<Object> NoHandlerFoundHandler(NoHandlerFoundException ex) {
        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .location(URI.create("/api/dvds"))
                .build();
    }
}




