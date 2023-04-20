package com.retail.dvdapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

/*
* Main class of the spring boot rest server
* Importing runtime hints is used for the native-image executable
*/

@SpringBootApplication
@ImportRuntimeHints(DvdApplicationHints.class)
public class DvdApplication {

    public static void main(String[] args) {
        SpringApplication.run(DvdApplication.class, args);
    }

}
