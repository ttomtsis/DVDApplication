package com.retail.dvdapplication.services;

import com.retail.dvdapplication.exceptions.DVDNotFoundException;
import com.retail.dvdapplication.repositories.DVD;
import com.retail.dvdapplication.repositories.DVDRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
* Application logic implementation. Services requests made by
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

    private static final Logger log = LoggerFactory.getLogger("SERVICE RESPONSE");
    public List<DVD> read() {
        return repository.findAll();
    }

    public Optional<DVD> read(long id) {
        Optional<DVD> requested_dvd = repository.findById(id);
        if ( requested_dvd.isPresent() ) {
            return requested_dvd;
        }
        else {
            throw new DVDNotFoundException(id);
        }
    }

    public Optional<DVD> read(String name) {
        Optional<DVD> requested_dvd = repository.findByName(name);
        if ( requested_dvd.isPresent() ) {
            return requested_dvd;
        }
        else {
            throw new DVDNotFoundException(name);
        }
    }

    public void create(DVD new_dvd) {
        log.info("DVD CREATION REQUEST RECEIVED");
        repository.save(new_dvd);
    }

    public void update(long id, DVD updated_dvd) { // Method Performance ok ?
        // ID EXISTS ?
        if ( repository.existsById(id) ) {
            log.info("DVD EXISTS");
            // IS IT THE SAME OBJECT ?
            DVD to_be_updated = repository.findById(id).get();
            if ( to_be_updated.getName().equals(updated_dvd.getName()) ) {
                log.info("IT IS SAME DVD");
                // SET NEW FIELDS
                if ( updated_dvd.getGenre() != "null" ) {
                    log.info("UPDATING GENRE with " + updated_dvd.getGenre());
                    to_be_updated.setGenre(updated_dvd.getGenre());
                }
/*                if ( updated_dvd.getReserve() != null ) {
                    log.info("UPDATING RESERVE");
                    to_be_updated.setReserve(updated_dvd.getReserve());
                }*/
                to_be_updated.setReserve(updated_dvd.getReserve());
            }
            else {
                throw new DVDNotFoundException(id, updated_dvd.getName());
            }

        }
        else {
            throw new DVDNotFoundException(id);
        }

    }

    public void delete(long id) {
        if ( repository.existsById(id) ) {
            repository.deleteById(id);
        }
        else {
            throw new DVDNotFoundException(id);
        }

    }


}
