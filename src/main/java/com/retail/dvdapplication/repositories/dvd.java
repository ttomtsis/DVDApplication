package com.retail.dvdapplication.repositories;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/*
* Domain object, represents DVD. Will be inserted to MySQL DB
* Manipulated by DVDService class
*/

@Entity
public class dvd {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String genre;
    private int reserve;

    protected dvd() {}

    dvd (String name, String genre, int reserve) {
        this.name = name;
        this.genre = genre;
        this.reserve = reserve;
    }

    // SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setReserve(int reserve) {
        this.reserve = reserve;
    }

    // GETTERS
    public Long getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public String getGenre() {
        return this.genre;
    }

    public int getReserve() {
        return this.reserve;
    }
}
