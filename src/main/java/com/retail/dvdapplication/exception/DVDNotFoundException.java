package com.retail.dvdapplication.exception;

public class DVDNotFoundException extends RuntimeException {

    public DVDNotFoundException(long id) {
        super("The DVD with ID " + id + " could not be found in the Database");
    }
    public DVDNotFoundException(String name) {
        super("The DVD with NAME: " + name + " could not be found in the Database");
    }
    public DVDNotFoundException(long id, String name) {
        super("The DVD with ID: " + id + " and NAME: " + name +" could not be found in the Database");
    }
}
