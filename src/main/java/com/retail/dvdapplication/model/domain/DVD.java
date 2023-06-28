package com.retail.dvdapplication.model.domain;

import com.retail.dvdapplication.controller.DVDController;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.lang.NonNull;

import java.util.Objects;

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
    private enum genre_type { ACTION, ROMANCE, COMEDY }
    @Enumerated(EnumType.STRING)
    private genre_type genre;
    @PositiveOrZero
    private Integer reserve;

    public DVD() {}

    public DVD(String name, String genre, int reserve) {
        this.name = name;
        this.genre = genre_type.valueOf(genre);
        this.reserve = reserve;
    }

    // Adds links to the object
    public void addLinks() {
        add(linkTo(methodOn(DVDController.class).searchDVDByID(this.id)).withSelfRel());
        add(linkTo(methodOn(DVDController.class).searchDVDByName(this.name, 0, 1)).withSelfRel());
        add(linkTo(methodOn(DVDController.class).deleteDVDByID(this.id)).withRel("Delete"));
        add(linkTo(methodOn(DVDController.class).updateDVDByID(this.id,null)).withRel("Update"));
        add(linkTo(methodOn(DVDController.class).searchAllDVDs(0, 10)).withRel("All DVDs"));
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
        String dvdGenre = String.valueOf(this.genre);
        if ( dvdGenre.equals("null") ) { // Happens if a genre is not provided
            return null;
        }
        return dvdGenre;
    }

    public Integer getReserve() {
        return this.reserve;
    }

    // UTILITY

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DVD dvdObj){

            return Objects.equals(this.id, dvdObj.id) && this.genre.equals(dvdObj.genre) && Objects.equals(this.reserve, dvdObj.reserve);

        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int) (this.id * this.genre.hashCode() * this.reserve);
    }

    @Override
    @NonNull
    public String toString() {
        return "DVD {" + "id=" + this.id + ", name='" + this.name + '\'' + ", genre='" + this.genre + '\'' + ", reserve='" + this.reserve + '\'' + '}';
    }
}
