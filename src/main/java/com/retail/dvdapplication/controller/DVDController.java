package com.retail.dvdapplication.controller;

import com.retail.dvdapplication.model.domain.DVD;
import com.retail.dvdapplication.model.representation.DVDModel;
import com.retail.dvdapplication.model.representation.DVDModelAssembler;
import com.retail.dvdapplication.model.service.DVDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
* Creates mappings for HTTP Methods and links them
* to respective service implementations of DVDService
*/
@RestController
@RequestMapping(value = "/api/v1/dvds")
public class DVDController {

    private final DVDService service; // Service class
    private static final Logger log = LoggerFactory.getLogger("DVD Controller");

    private final DVDModelAssembler assembler;
    public DVDController(DVDService service, DVDModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping("")
    public ResponseEntity<DVDModel> createDVD(@RequestBody DVD newDVD) {
        log.info("New DVD Creation Request: " + newDVD.toString());
        DVD createdDVD = service.createDVD(newDVD);
        return new ResponseEntity<>(assembler.toModel(createdDVD), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<PagedModel<DVDModel>> searchAllDVDs(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        log.info("New DVD Search Request: Search All");
        Pageable pageable = PageRequest.of(page, size);
        Page<DVD> dvdPage = service.searchAllDVDs(pageable);
        PagedModel<DVDModel> myModel = assembler.createPagedModelForSearchAll(dvdPage);
        return new ResponseEntity<>(myModel, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<PagedModel<DVDModel>> searchDVDByName(@RequestParam String name, @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        log.info("New DVD Search Request: Search name - " + name);
        Pageable pageable = PageRequest.of(page, size);
        Page<DVD> dvdPage = service.searchDVDByName(name, pageable);
        PagedModel<DVDModel> myModel = assembler.createPagedModelForSearchByName(dvdPage, name);
        return new ResponseEntity<>(myModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DVDModel> searchDVDByID(@PathVariable long id) {
        log.info("New DVD Search Request: Search id - " + id);
        DVD requestedDVD = service.searchDVDByID(id);
        return new ResponseEntity<>(assembler.toModel(requestedDVD), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DVDModel> updateDVDByID(@PathVariable long id, @RequestBody DVD newDVD) {
            log.info("New DVD Update Request: Update DVD - " + id);
            DVD updatedDVD = service.updateDVDByID(id, newDVD);
            return new ResponseEntity<>(assembler.toModel(updatedDVD),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDVDByID(@PathVariable long id) {
        log.info("New DVD Delete Request: Delete DVD - " + id);
        service.deleteDVDByID(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
