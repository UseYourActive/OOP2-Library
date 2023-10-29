package com.library.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "authors")
//@Builder
@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
public class Author {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "second_name", nullable = false)
    private String secondName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private Timestamp dateOfBirth;

    @Column(name = "country", nullable = false)
    private String country;

    @OneToMany
    @Column(name = "books", nullable = false)
    private List<Book> books;

    public Author(UUID id, String firstName, String secondName, String lastName, Timestamp dateOfBirth, String country, List<Book> books) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.books = books;
    }

    public static class AuthorBuilder {
        private UUID id;
        private String firstName;
        private String secondName;
        private String lastName;
        private Timestamp dateOfBirth;
        private String country;
        private List<Book> books;

        public AuthorBuilder() {
        }

        public AuthorBuilder id(final UUID id){
            this.id = id;
            return this;
        }

        public AuthorBuilder firstName(final String firstName){
            this.firstName = firstName;
            return this;
        }

        public AuthorBuilder secondName(final String secondName){
            this.secondName = secondName;
            return this;
        }

        public AuthorBuilder lastName(final String lastName){
            this.lastName = lastName;
            return this;
        }

        public AuthorBuilder dateOfBirth(final Timestamp dateOfBirth){
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public AuthorBuilder country(final String country){
            this.country = country;
            return this;
        }

        public AuthorBuilder books(final List<Book> books){
            this.books = books;
            return this;
        }

        public Author build(){
            return new Author(this.id, this.firstName, this.secondName, this.lastName, this.dateOfBirth, this.country, this.books);
        }
    }

    public static AuthorBuilder builder(){
        return new AuthorBuilder();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Timestamp dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
