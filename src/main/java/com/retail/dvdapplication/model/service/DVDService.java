package com.retail.dvdapplication.model.service;

import com.retail.dvdapplication.exception.DVDNotFoundException;
import com.retail.dvdapplication.model.domain.DVD;
import com.retail.dvdapplication.model.repository.DVDRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/*
* Application logic implementation.
* Service requests made by the DVDController.
* Utilizes DVDRepository to issue queries to the MySQL DB.
*/
@Service
@Transactional
@Validated
public class DVDService {

    private final DVDRepository repository;

    public DVDService(DVDRepository repository) {
        this.repository = repository;
    }

    private static final Logger log = LoggerFactory.getLogger("DVD Service");

    // Return all dvds in database
    public Page<DVD> searchAllDVDs(Pageable pageable) {
        Page<DVD> searchResults = repository.findAll(pageable);
        for ( DVD d : searchResults ) {
            d.addLinks();
        }
        return searchResults;
    }

    // Return dvd with matching id
    public DVD searchDVDByID(@Positive long id) {
        DVD dvd = repository.findById(id).orElseThrow(() -> new DVDNotFoundException(id));
        dvd.addLinks();
        return dvd;
    }

    // Return all dvds whose titles are similar to the requested name
    public Page<DVD> searchDVDByName(@NotBlank String name, @NonNull Pageable pageable) {
        Page<DVD> searchResults = repository.findByNameContainingIgnoreCase(name, pageable);
        for ( DVD d : searchResults ) {
            d.addLinks();
        }
        return searchResults;
    }

    // Create a new DVD, if DVD cannot be saved to DB then exception occurs
    public DVD createDVD(@Valid DVD newDVD) {
        repository.save(newDVD);
        newDVD.addLinks();
        return newDVD;
    }

    // Update a DVD
    public DVD updateDVDByID(@Positive long id, @Valid DVD newDVD) {
        // Check if the DVD exists
        DVD existingDVD = repository.findById(id).orElseThrow(() -> new DVDNotFoundException(id));

        // Check if the DVD actually requires updating
        if ( existingDVD.equals(newDVD) ) { return existingDVD; }

        // Update fields
        if ( newDVD.getGenre() != null ) {
            existingDVD.setGenre(newDVD.getGenre());
        }
       if ( newDVD.getReserve() != null ) {
           existingDVD.setReserve(newDVD.getReserve());
        }
        return existingDVD;

    }

    // Delete a DVD
    public void deleteDVDByID(@Positive long id) {
        if ( repository.existsById(id) ) {
            repository.deleteById(id);
        }
        else {
            throw new DVDNotFoundException(id);
        }

    }


}
