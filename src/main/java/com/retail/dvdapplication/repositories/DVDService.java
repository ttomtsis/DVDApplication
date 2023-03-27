package com.retail.dvdapplication.repositories;

import com.retail.dvdapplication.exceptions.DVDNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.cfg.NotYetImplementedException;
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

    public List<dvd> read() {
        return repository.findAll();
    }

    public Optional<dvd> read(long id) {
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

    public void create(Object new_dvd) {
        dvd d = (dvd) new_dvd;
        repository.save(d);
    }

    public void update() {
        throw new NotYetImplementedException("This function has not yet been implemented");
    }

    public void update(long id, Object updated_dvd) {
    }

    public void delete(long id) {
        repository.deleteById(id);
    }


}
