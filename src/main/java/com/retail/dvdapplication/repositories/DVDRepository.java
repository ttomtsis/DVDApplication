package com.retail.dvdapplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
* Provides methods for CRUD operations on DVD objects
* Implemented by DVDService
*/
@Repository
public interface DVDRepository extends JpaRepository<DVD, Long> {
    Optional<DVD> findByName(String name);
}
