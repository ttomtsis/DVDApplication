package com.retail.dvdapplication.service;

import com.retail.dvdapplication.domain.DVD;
import com.retail.dvdapplication.exception.DVDNotFoundException;
import com.retail.dvdapplication.exception.MissingRequiredDataException;
import com.retail.dvdapplication.repository.DVDRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/*
* Application logic implementation. Service requests made by
* DVDController RESTController. Utilizes DVDRepository repository
* to issue queries to the MySQL DB.
*/
@Service
@Transactional
public class DVDService {

    private final DVDRepository repository;

    public DVDService(DVDRepository repository) {
        this.repository = repository;
    }

    private static final Logger log = LoggerFactory.getLogger("DVD Service");

    // Return all dvds in database
    public List<DVD> searchAllDVDs() {
        List<DVD> searchResults = repository.findAll();
        for ( DVD d : searchResults ) {
            d.addLinks();
        }
        return searchResults;
    }

    // Return dvd with matching id
    public DVD searchDVDByID(long id) {
        DVD dvd = repository.findById(id).orElseThrow(() -> new DVDNotFoundException(id));
        dvd.addLinks();
        return dvd;
    }

    // Return all dvds whose titles are similar to the requested name
    public List<DVD> searchDVDByName(String name) {
        if ( name.equals("") ) {
            throw new MissingRequiredDataException();
        }
        List<DVD> searchResults = repository.findByNameContainingIgnoreCase(name);
        for ( DVD d : searchResults ) {
            d.addLinks();
        }
        return searchResults;
    }

    // Create a new DVD, if DVD cannot be saved to DB then exception occurs
    public DVD createDVD(DVD newDVD) {
        repository.save(newDVD);
        newDVD.addLinks();
        return newDVD;
    }

    // Update a DVD
    // Also check issue #17 regarding the 'name' field
    // https://github.com/ttomtsis/DVDApplication/issues/17
    public DVD updateDVDByID(long id, DVD newDVD) {
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
    public void deleteDVDByID(long id) {
        if ( repository.existsById(id) ) {
            repository.deleteById(id);
        }
        else {
            throw new DVDNotFoundException(id);
        }

    }


}
