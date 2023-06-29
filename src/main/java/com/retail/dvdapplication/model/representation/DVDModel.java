package com.retail.dvdapplication.model.representation;

import org.springframework.hateoas.RepresentationModel;

public class DVDModel extends RepresentationModel<DVDModel> {
    private Long id;
    private String name;
    private enum genre_type { ACTION, ROMANCE, COMEDY }
    private genre_type genre;
    private Integer reserve;

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
    public void setId(Long Id) {
        this.id = Id;
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
}
