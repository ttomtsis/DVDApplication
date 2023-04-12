package com.retail.dvdapplication.service;

import com.retail.dvdapplication.exception.DVDNotFoundException;
import com.retail.dvdapplication.domain.DVD;
import com.retail.dvdapplication.repository.DVDRepository;
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

    private static final Logger log = LoggerFactory.getLogger("DVD Service");
    public List<DVD> searchAllDVDs() {
        return repository.findAll();
    }

    public Optional<DVD> searchDVDByID(long id) {
        Optional<DVD> requested_dvd = repository.findById(id);
        if ( requested_dvd.isPresent() ) {
            return requested_dvd;
        }
        else {
            throw new DVDNotFoundException(id);
        }
    }

    public void createDVD(DVD new_dvd) {
        repository.save(new_dvd);
    }

    public void updateDVDByID(long id, DVD updated_dvd) { // Method Performance ok ?
        if ( repository.existsById(id) ) {
            DVD to_be_updated = repository.findById(id).get();

            // SET NEW FIELDS
            if ( updated_dvd.getGenre() != "null" ) {
                to_be_updated.setGenre(updated_dvd.getGenre());
            }
            // Check github issue #1 https://github.com/ttomtsis/DVDApplication/issues/1
           if ( updated_dvd.getReserve() != 0 ) {
                to_be_updated.setReserve(updated_dvd.getReserve());
            }
            to_be_updated.setReserve(updated_dvd.getReserve());

        }
        else {
            throw new DVDNotFoundException(id);
        }

    }

    public void deleteDVDByID(long id) {
        if ( repository.existsById(id) ) {
            repository.deleteById(id);
        }
        else {
            throw new DVDNotFoundException(id);
        }

    }


}
