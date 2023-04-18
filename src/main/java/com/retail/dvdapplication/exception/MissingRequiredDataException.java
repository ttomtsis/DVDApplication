package com.retail.dvdapplication.exception;

/*
* Exception that is thrown when a user tries to perform a function
* but has not provided the necessary data: eg. Update DVD by id but has not provided a new dvd
* */
public class MissingRequiredDataException extends RuntimeException {
    public MissingRequiredDataException(){super();}
    public MissingRequiredDataException(String msg) {
        super(msg);
    }
}
