package com.retail.dvdapplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
* Provides methods for CRUD operations on dvd objects
* Implemented by DVDService
*/
@Repository
public
interface DVDRepository extends JpaRepository<dvd, Long> {
    List<dvd> findByName(String name);
}
