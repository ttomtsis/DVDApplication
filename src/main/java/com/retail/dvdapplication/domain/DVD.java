package com.retail.dvdapplication.domain;

import com.retail.dvdapplication.controller.DVDController;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/*
* Domain object, represents DVD. Will be inserted to MySQL DB
* Manipulated by DVDService class
*/

@Entity
@Table(name = "dvd", uniqueConstraints=@UniqueConstraint(columnNames="name"))
public class DVD  extends RepresentationModel<DVD> {
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

    public DVD() {}

    public DVD(String name, String genre, int reserve) {
        this.name = name;
        this.genre = genre_type.valueOf(genre);
        this.reserve = reserve;
    }

    public void addLinks() {
        add(linkTo(methodOn(DVDController.class).searchDVDByID(this.id)).withSelfRel());
        add(linkTo(methodOn(DVDController.class).searchDVDByName(this.name)).withSelfRel());
        add(linkTo(methodOn(DVDController.class).deleteDVDByID(this.id)).withRel("Delete"));
        add(linkTo(methodOn(DVDController.class).updateDVDByID(this.id,null)).withRel("Update"));
        add(linkTo(methodOn(DVDController.class).searchAllDVDs()).withRel("All DVDs"));
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
    public boolean equals(Object a) {
        DVD dvd = (DVD) a;
        if (this.id == dvd.id && this.genre.equals(dvd.genre) && this.reserve == dvd.reserve) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int) (this.id * this.genre.hashCode() * this.reserve);
    }

    @Override
    public String toString() {
        return "DVD {" + "id=" + this.id + ", name='" + this.name + '\'' + ", genre='" + this.genre + '\'' + ", reserve='" + this.reserve + '\'' + '}';
    }
}
