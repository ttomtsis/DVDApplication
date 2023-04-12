package com.retail.dvdapplication.repository;

import com.retail.dvdapplication.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByName(String name);

    Employee findByNameAndPassword(String name, String password);
}
