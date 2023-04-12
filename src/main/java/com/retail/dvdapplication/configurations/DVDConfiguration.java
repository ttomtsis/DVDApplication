package com.retail.dvdapplication.configurations;

import com.retail.dvdapplication.repositories.EmployeeRepository;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DVDConfiguration {
   private EmployeeRepository repository;

    DVDConfiguration ( EmployeeRepository repository ) {
        this.repository = repository;
    }

}
