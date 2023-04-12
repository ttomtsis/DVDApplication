package com.retail.dvdapplication.exception;

import org.springframework.security.core.AuthenticationException;

public class MissingCredentialsException extends AuthenticationException {
    public MissingCredentialsException(String cause) {
        super(cause);
    }

}
