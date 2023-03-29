package com.retail.dvdapplication.repositories;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;

/*
* Domain object, represents DVD. Will be inserted to MySQL DB
* Manipulated by DVDService class
*/

@Entity
@Table(name = "dvd", uniqueConstraints=@UniqueConstraint(columnNames="name"))
public class DVD {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private String name;
    private enum genre_type { Action, Romance, Comedy }
    @Enumerated(EnumType.STRING)
    private genre_type genre;
    @PositiveOrZero(message = "DVD Reserves cannot be less than zero")
    private int reserve;

    public DVD() {}

    public DVD(String name, String genre, int reserve) {
        this.name = name;
        this.genre = genre_type.valueOf(genre);
        this.reserve = reserve;
    }

    // SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre_type.valueOf(genre);
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
        return String.valueOf(this.genre);
    }

    public int getReserve() {
        return this.reserve;
    }

    // UTILITY
    @Override
    public String toString() {
        return "DVD {" + "id=" + this.id + ", name='" + this.name + '\'' + ", genre='" + this.genre + '\'' + ", reserve='" + this.reserve + '\'' + '}';
    }
}
