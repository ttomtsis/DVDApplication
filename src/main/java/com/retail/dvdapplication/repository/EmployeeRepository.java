package com.retail.dvdapplication.repository;

import com.retail.dvdapplication.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Provides methods for CRUD operations on Employee objects
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByName(String name);

    Employee findByNameAndPassword(String name, String password);
}
