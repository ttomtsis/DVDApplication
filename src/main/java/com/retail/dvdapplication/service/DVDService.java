package com.retail.dvdapplication.service;

import com.retail.dvdapplication.domain.DVD;
import com.retail.dvdapplication.exception.DVDNotFoundException;
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
        return repository.findAll();
    }

    // Return dvd with matching id or throw exception
    public DVD searchDVDByID(long id) {
        return repository.findById(id).orElseThrow(() -> new DVDNotFoundException(id));
    }

    // Return all dvds whose titles match the requested name
    public List<DVD> searchDVDByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    // Create a new DVD
    public DVD createDVD(DVD newDVD) {
        repository.save(newDVD);
        // If DVD cannot be saved to DB then exception occurs and method does not return
        return newDVD;
    }

    // Update a DVD
    public DVD updateDVDByID(long id, DVD newDVD) {
        // Check if the DVD exists
        DVD updatedDVD = repository.findById(id).orElseThrow(() -> new DVDNotFoundException(id));

        // Check if the DVD actually requires updating
        if ( updatedDVD.equals(newDVD) ) { return null; }

        // Update fields
        if ( !newDVD.getGenre().equals( "null") ) {
            updatedDVD.setGenre(newDVD.getGenre());
        }
        // Check github issue #1 https://github.com/ttomtsis/DVDApplication/issues/1
       if ( newDVD.getReserve() != 0 ) {
           updatedDVD.setReserve(newDVD.getReserve());
        }
        return updatedDVD;

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
