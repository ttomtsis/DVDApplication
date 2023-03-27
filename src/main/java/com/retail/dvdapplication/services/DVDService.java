package com.retail.dvdapplication.services;

import com.retail.dvdapplication.repositories.dvd;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DVDService {

    public List<dvd> read() {
        return null;
    }


    public void create(Object dvd) {
    }

    public List<dvd> read(long id) {
        return null;
    }

    public List<dvd> read(String name) {
        return null;
    }

    public void update() {

    }

    public void update(long id, Object updated_dvd) {
    }

    public void delete(long id) {

    }


}
