package com.retail.dvdapplication.repositories;

import jakarta.persistence.*;

@Entity
@Table(name = "user", uniqueConstraints=@UniqueConstraint(columnNames="name"))
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private String name;
    @Column(nullable=false)
    private String password;

    public User() {}

    public User(String name, String password) {
     this.name = name;
     this.password = password;
    }

    // SETTERS
    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    // GETTERS
    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public Long getId() {
        return id;
    }

    // UTILITY
    @Override
    public String toString() {
        return "USER DETAILS\n: " + "Id: " + this.id + "\nName: " + this.name + "\nPassword: " + this.password;
    }
}
