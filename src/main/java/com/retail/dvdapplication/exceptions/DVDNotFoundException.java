package com.retail.dvdapplication.exceptions;

import java.util.function.Supplier;

public class DVDNotFoundException extends RuntimeException {

    public DVDNotFoundException(long id) {
        super("The DVD with ID " + id + "could not be found in the DB");
    }
}
