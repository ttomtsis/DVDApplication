package com.retail.dvdapplication.model.repository;

import com.retail.dvdapplication.model.domain.DVD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
* Provides methods for CRUD operations on DVD objects
* Implemented by DVDService
*/
@Repository
public interface DVDRepository extends JpaRepository<DVD, Long> {

    // Finds all DVDs that match the name given, is NOT case sensitive
    // eg: For name=wulf, the following DVDs will be returned, Beowulf, Beowulf 132, wulf 132
    List<DVD> findByNameContainingIgnoreCase(String name);
}
