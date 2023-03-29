package com.retail.dvdapplication.controllers;

import com.retail.dvdapplication.repositories.DVD;
import com.retail.dvdapplication.services.DVDService;
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

    @PostMapping("/dvd/new")
    public void create(@RequestBody DVD new_dvd) {
        service.create(new_dvd);
    }

    @GetMapping("/dvd/all")
    public List<DVD> read_all() {
        return service.read();
    }

    @GetMapping("/dvd/id/{id}")
    public Optional<DVD> read_by_id(@PathVariable long id) {
        return service.read(id);
    }

    @GetMapping("/dvd/name/{name}")
    public Optional<DVD> read_by_name(@PathVariable String name) {
        return service.read(name);
    }

    @PutMapping("/dvd/id/{id}")
    public void update_by_id(@PathVariable long id, @RequestBody DVD updated_dvd) {
        service.update(id, updated_dvd);
    }

    @DeleteMapping("dvd/id/{id}")
    public void delete_by_id(@PathVariable long id) {
        service.delete(id);
    }

    // public or default access modifier?
    public DVDService getService() {
        return service;
    }

    // implement setters and remove final?
/*  public void setService( DVDService new_service){
        this.service = new_service;
    }*/
}
