package com.retail.dvdapplication.repositories;

import com.retail.dvdapplication.exceptions.DVDNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.cfg.NotYetImplementedException;
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

    private static final Logger log = LoggerFactory.getLogger("Query Response");
    public List<dvd> read() {
        log.info(repository.findAll().toString());
        return repository.findAll();
    }

    public Optional<dvd> read(long id) {
        log.info("inside method");
        Optional<dvd> requested_dvd = repository.findById(id);
        if ( requested_dvd.isPresent() ) {
            System.out.println("DVD with ID " + id + " was found on DB");
            return requested_dvd;
        }
        else {
            System.out.println(" DVD not found");
            throw new DVDNotFoundException(id);
        }
    }

    public List<dvd> read(String name) {
        return repository.findByName(name);
    }

    public void create(dvd new_dvd) {
        repository.save(new_dvd);
    }

    public void update() {
        throw new NotYetImplementedException("This function has not yet been implemented");
    }

    public void update(long id, dvd updated_dvd) { // Method Performance ok ?
        // ID EXISTS ?
        if ( repository.existsById(id) ) {
            log.info("DVD EXISTS");
            // IS IT THE SAME OBJECT ?
            dvd to_be_updated = repository.findById(id).get();
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
                // THROW InvalidDVDException
            }

        }
        else {
            // THROW DVDNotFoundException
        }

    }

    public void delete(long id) {
        repository.deleteById(id);
    }


}
