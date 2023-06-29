package com.retail.dvdapplication.model.repository;

import com.retail.dvdapplication.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Provides methods for CRUD operations on Employee objects
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByName(String name);

    Employee findByNameAndPassword(String name, String password);
}
