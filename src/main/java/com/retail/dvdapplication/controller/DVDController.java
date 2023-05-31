package com.retail.dvdapplication.controller;

import com.retail.dvdapplication.domain.DVD;
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
@RequestMapping(value = "/api/dvds")
public class DVDController {

    private final DVDService service; // Service class
    private static final Logger log = LoggerFactory.getLogger("DVD Controller");

    public DVDController(DVDService service) {
        this.service = service;
    }

    @PostMapping("")
    public ResponseEntity<DVD> createDVD(@RequestBody DVD new_dvd) {
        log.info("New DVD Creation Request: " + new_dvd.toString());
        return new ResponseEntity<>(service.createDVD(new_dvd), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<DVD>> searchAllDVDs() {
        log.info("New DVD Search Request: Search All");
        return new ResponseEntity<>(service.searchAllDVDs(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DVD>> searchDVDByName(@RequestParam String name) {
        log.info("New DVD Search Request: Search name - " + name);
        return new ResponseEntity<>(service.searchDVDByName(name), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DVD> searchDVDByID(@PathVariable long id) {
        log.info("New DVD Search Request: Search id - " + id);
        return new ResponseEntity<>(service.searchDVDByID(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DVD> updateDVDByID(@PathVariable long id, @RequestBody DVD newDVD) {
            log.info("New DVD Update Request: Update DVD - " + id);
            return new ResponseEntity<>(service.updateDVDByID(id, newDVD),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDVDByID(@PathVariable long id) {
        log.info("New DVD Delete Request: Delete DVD - " + id);
        service.deleteDVDByID(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
