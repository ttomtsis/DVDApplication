package com.retail.dvdapplication.exception;

public class MissingRequiredDataException extends RuntimeException {
    public MissingRequiredDataException(){super();}
    public MissingRequiredDataException(String msg) {
        super(msg);
    }
}
