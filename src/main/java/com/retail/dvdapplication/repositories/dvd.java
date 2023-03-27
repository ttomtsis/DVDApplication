package com.retail.dvdapplication.repositories;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

/*
* Domain object, represents DVD. Will be inserted to MySQL DB
* Manipulated by DVDService class
*/

@Entity
public class dvd {
    @Id
    @GeneratedValue
    private Long id;
    @Nonnull
    @Column(unique=true)
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
    void setName(String name) {
        this.name = name;
    }

    void setGenre(String genre) {
        this.genre = genre;
    }

    void setReserve(int reserve) {
        this.reserve = reserve;
    }

    // GETTERS
    Long getId() {
        return id;
    }

    String getName() {
        return this.name;
    }

    String getGenre() {
        return this.genre;
    }

    int getReserve() {
        return this.reserve;
    }

    // UTILITY
    @Override
    public String toString() {
        return "DVD {" + "id=" + this.id + ", name='" + this.name + '\'' + ", genre='" + this.genre + '\'' + ", reserve='" + this.reserve + '\'' + '}';
    }
}
