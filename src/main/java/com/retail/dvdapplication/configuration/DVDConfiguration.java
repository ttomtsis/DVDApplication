package com.retail.dvdapplication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
<<<<<<< HEAD
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
=======
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
>>>>>>> master

/*
* Basic Configuration for the DVDApplication
* */
@Configuration
public class DVDConfiguration {
    @Bean
<<<<<<< HEAD
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

=======
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
>>>>>>> master
}
