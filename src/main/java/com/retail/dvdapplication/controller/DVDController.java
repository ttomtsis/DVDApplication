package com.retail.dvdapplication.controller;

import com.retail.dvdapplication.domain.DVD;
import com.retail.dvdapplication.exception.MissingRequiredDataException;
import com.retail.dvdapplication.service.DVDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* Creates mappings for HTTP Methods and links them
* to respective service implementations of DVDService
*/
@RestController
@RequestMapping(value = "/api")
public class DVDController {

    private final DVDService service; // Service class
    private static final Logger log = LoggerFactory.getLogger("DVD Controller");

    public DVDController(DVDService service) {
        this.service = service;
    }

    @PostMapping("/dvds")
    public ResponseEntity<DVD> createDVD(@RequestBody DVD new_dvd) {
        log.info("New DVD Creation Request: " + new_dvd.toString());
        DVD created = service.createDVD(new_dvd);
        created.addLinks();
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/dvds")
    public ResponseEntity<List<DVD>> searchAllDVDs() {
        log.info("New DVD Search Request: Search All");
        List<DVD> searchResults = service.searchAllDVDs();
        for ( DVD d : searchResults ) {
            d.addLinks();
        }
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }

    @GetMapping("/dvds/search")
    public ResponseEntity<List<DVD>> searchDVDByName(@RequestParam String name) {
        if ( name.equals("") ) {
            throw new MissingRequiredDataException();
        }
        log.info("New DVD Search Request: Search name - " + name);
        List<DVD> searchResults = service.searchDVDByName(name);
        for ( DVD d : searchResults ) {
            d.addLinks();
        }
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }

    @GetMapping("/dvds/{id}")
    public ResponseEntity<DVD> searchDVDByID(@PathVariable long id) {
        log.info("New DVD Search Request: Search id - " + id);
        DVD dvd = service.searchDVDByID(id);
        dvd.addLinks();
        return new ResponseEntity<>(dvd, HttpStatus.OK);
    }

    @PutMapping("/dvds/{id}")
    public ResponseEntity<DVD> updateDVDByID(@PathVariable long id, @RequestBody DVD newDVD) {
        DVD updatedDVD = service.updateDVDByID(id, newDVD);
        if ( updatedDVD == null ){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            log.info("New DVD Update Request: Update DVD - " + id);
            service.updateDVDByID(id, newDVD);
            updatedDVD.addLinks();
            return new ResponseEntity<>(updatedDVD,HttpStatus.OK);
        }
    }

    @DeleteMapping("dvds/{id}")
    public ResponseEntity<Void> deleteDVDByID(@PathVariable long id) {
        log.info("New DVD Delete Request: Delete DVD - " + id);
        service.deleteDVDByID(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
