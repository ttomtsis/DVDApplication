package com.retail.dvdapplication.model.repository;

import com.retail.dvdapplication.model.entity.DVD;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

/*
* Provides methods for CRUD operations on DVD objects
* Used by DVDService
*/
@Repository
public interface DVDRepository extends JpaRepository<DVD, Long> {

    // Finds all DVDs that match the name given, is NOT case-sensitive
    // eg: For name=wulf, the following DVDs will be returned, Beowulf, Beowulf 132, wulf 132
    Page<DVD> findByNameContainingIgnoreCase(String name, Pageable pageable);
    @Override
    @NonNull
    Page<DVD> findAll(@NonNull Pageable pageable);

}
