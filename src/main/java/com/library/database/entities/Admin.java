package com.library.database.entities;

import com.library.database.entities.base.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "admins")
//@Builder
//@Getter
//@Setter
//@AllArgsConstructor(access = AccessLevel.PROTECTED)
//@NoArgsConstructor
public class Admin extends User {
    private List<Operator> listOfOperators;
    private List<Book> listOfBooks;
}
