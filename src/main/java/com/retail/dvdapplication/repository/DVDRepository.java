package com.retail.dvdapplication.repository;

import com.retail.dvdapplication.domain.DVD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
* Provides methods for CRUD operations on DVD objects
* Implemented by DVDService
*/
@Repository
public interface DVDRepository extends JpaRepository<DVD, Long> {
    List<DVD> findByNameContainingIgnoreCase(String name);
}