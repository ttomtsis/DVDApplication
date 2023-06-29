package com.retail.dvdapplication.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.lang.NonNull;

import java.util.Objects;

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
        return this.id;
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
