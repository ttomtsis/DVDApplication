package com.retail.dvdapplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByName(String name);

    Employee findByNameAndPassword(String name, String password);
}
