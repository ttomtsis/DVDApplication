package com.retail.dvdapplication.repositories;

import jakarta.persistence.*;

import javax.validation.constraints.PositiveOrZero;

/*
* Domain object, represents DVD. Will be inserted to MySQL DB
* Manipulated by DVDService class
*/

@Entity
@Table(name = "dvd", uniqueConstraints=@UniqueConstraint(columnNames="name"))
public class dvd {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private String name;
    private enum genre_type { Action, Romance, Comedy }
    @Enumerated(EnumType.STRING)
    private genre_type genre;
    @PositiveOrZero
    private int reserve;

    protected dvd() {}

    dvd (String name, String genre, int reserve) {
        this.name = name;
        this.genre = genre_type.valueOf(genre);
        this.reserve = reserve;
    }

    // SETTERS
    void setName(String name) {
        this.name = name;
    }

    void setGenre(String genre) {
        this.genre = genre_type.valueOf(genre);
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
        return String.valueOf(this.genre);
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
