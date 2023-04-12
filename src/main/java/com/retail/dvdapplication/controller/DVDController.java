package com.retail.dvdapplication.controller;

import com.retail.dvdapplication.domain.DVD;
import com.retail.dvdapplication.service.DVDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
* Creates mappings for HTTP Methods and links them
* to respective service implementations of DVDService
* */
@RestController
@RequestMapping(value = "/api")
public class DVDController {

    private final DVDService service;
    private static final Logger log = LoggerFactory.getLogger("CONTROLLER RESPONSE");

    public DVDController(DVDService service) {
        this.service = service;
    }

    @PostMapping("/dvd/create")
    public void create(@RequestBody DVD new_dvd) {
        log.info("NEW CREATION REQUEST");
        service.create(new_dvd);
    }

    @GetMapping("/dvd/search/all")
    public List<DVD> read_all() {
        return service.read();
    }

    @GetMapping("/dvd/search/id/{id}")
    public Optional<DVD> read_by_id(@PathVariable long id) {
        return service.read(id);
    }

    @GetMapping("/dvd/search/name/{name}")
    public Optional<DVD> read_by_name(@PathVariable String name) {
        return service.read(name);
    }

    @PutMapping("/dvd/update/id/{id}")
    public void update_by_id(@PathVariable long id, @RequestBody DVD updated_dvd) {
        service.update(id, updated_dvd);
    }

    @DeleteMapping("dvd/delete/id/{id}")
    public void delete_by_id(@PathVariable long id) {
        service.delete(id);
    }

    public DVDService getService() {
        return service;
    }

}
