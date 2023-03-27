package com.retail.dvdapplication.controllers;

import com.retail.dvdapplication.repositories.dvd;
import com.retail.dvdapplication.services.DVDService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* Creates mappings for HTTP Methods and links them
* to respective service implementations of DVDService
* */
@RestController
public class DVDController {

    private final DVDService service;

    public DVDController(DVDService service) {
        this.service = service;
    }

    @PostMapping("/dvd")
    public void create(@RequestBody Object dvd) {
        service.create(dvd);
    }

    @GetMapping("/dvd")
    public List<dvd> read() {
        return service.read();
    }

    @GetMapping("/dvd/{id}")
    public List<dvd> read_by_id(@PathVariable long id) {
        return service.read(id);
    }

    @GetMapping("/dvd/{name}")
    public List<dvd> read_by_name(@PathVariable String name) {
        return service.read(name);
    }

    @PutMapping("/dvd/{id}")
    public void update_by_id(@PathVariable long id, @RequestBody Object updated_dvd) {
        service.update(id, updated_dvd);
    }

    @DeleteMapping("dvd/{id}")
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
