package com.retail.dvdapplication.configuration;

import com.retail.dvdapplication.repository.EmployeeRepository;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DVDConfiguration {
   private EmployeeRepository repository;

    DVDConfiguration ( EmployeeRepository repository ) {
        this.repository = repository;
    }

}
