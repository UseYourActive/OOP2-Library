package com.library.responses;

import com.library.operation.base.OperationOutput;
import lombok.*;

import java.util.List;

//@Getter
//@Setter(AccessLevel.PRIVATE)
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Builder
//@AllArgsConstructor
public class CreateNewAuthorResponse implements OperationOutput {
    private String firstName;
    private String secondName;
    private String lastName;
    private String dateOfBirth;
    private String country;
    private List<String> books;

    public CreateNewAuthorResponse(String firstName, String secondName, String lastName, String dateOfBirth, String country, List<String> books) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.books = books;
    }

    public static CreateNewAuthorResponseBuilder builder(){
        return new CreateNewAuthorResponseBuilder();
    }

    public static class CreateNewAuthorResponseBuilder {
        private String firstName;
        private String secondName;
        private String lastName;
        private String dateOfBirth;
        private String country;
        private List<String> books;

        public CreateNewAuthorResponseBuilder firstName(final String firstName){
            this.firstName = firstName;
            return this;
        }

        public CreateNewAuthorResponseBuilder secondName(final String secondName){
            this.secondName = secondName;
            return this;
        }

        public CreateNewAuthorResponseBuilder lastName(final String lastName){
            this.lastName = lastName;
            return this;
        }

        public CreateNewAuthorResponseBuilder dateOfBirth(final String dateOfBirth){
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public CreateNewAuthorResponseBuilder country(final String country){
            this.country = country;
            return this;
        }

        public CreateNewAuthorResponseBuilder books(final List<String> books){
            this.books = books;
            return this;
        }

        public CreateNewAuthorResponse build(){
            return new CreateNewAuthorResponse(this.firstName, this.secondName, this.lastName, this.dateOfBirth, this.country, this.books);
        }
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getBooks() {
        return books;
    }

    public void setBooks(List<String> books) {
        this.books = books;
    }
}
