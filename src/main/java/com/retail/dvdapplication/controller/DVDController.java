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
* */
@RestController
@RequestMapping(value = "/api")
public class DVDController {

    private final DVDService service;
    private static final Logger log = LoggerFactory.getLogger("DVD CONTROLLER");

    public DVDController(DVDService service) {
        this.service = service;
    }

    @PostMapping("/dvds")
    public ResponseEntity<String> createDVD(@RequestBody DVD new_dvd) {
        log.info("New DVD Creation Request");
        service.createDVD(new_dvd);
        return new ResponseEntity<>("DVD Creation Successful!", HttpStatus.OK);
    }

    @GetMapping("/dvds")
    public ResponseEntity<List<DVD>> returnAllDVDs() {
        log.info("New DVD Search Request: Search All");
        List<DVD> dvd_list = service.searchAllDVDs();
        return new ResponseEntity<>(dvd_list, HttpStatus.OK);
    }

    @GetMapping("/dvds/{id}")
    public ResponseEntity<DVD> searchDVDByID(@PathVariable long id) {
        log.info("New DVD Search Request: Search id - " + id);
        DVD d = service.searchDVDByID(id).get();
        return new ResponseEntity<>(d, HttpStatus.OK);
    }

    @PutMapping("/dvds/{id}")
    public ResponseEntity<String> updateDVDByID(@PathVariable long id, @RequestBody DVD updated_dvd) {
        if ( updated_dvd.getGenre() == null && updated_dvd.getReserve() == 0 ){
            return new ResponseEntity<>("Please provide the values that you want to update",HttpStatus.BAD_REQUEST);
        }
        else {
            log.info("New DVD Update Request: Update DVD - " + id);
            service.updateDVDByID(id, updated_dvd);
            return new ResponseEntity<>("DVD with ID: " + id + " updated successfully",HttpStatus.OK);
        }
    }

    @DeleteMapping("dvds/{id}")
    public ResponseEntity<String> deleteDVDByID(@PathVariable long id) {
        log.info("New DVD Delete Request: Delete DVD - " + id);
        service.deleteDVDByID(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
