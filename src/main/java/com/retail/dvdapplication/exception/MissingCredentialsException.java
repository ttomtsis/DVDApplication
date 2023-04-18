package com.retail.dvdapplication.exception;

import org.springframework.security.core.AuthenticationException;

/*
* Exception that is thrown when the user does not provide either a username or a password
*/
public class MissingCredentialsException extends AuthenticationException {
    public MissingCredentialsException(String cause) {
        super(cause);
    }

}
