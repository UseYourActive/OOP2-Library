package com.library.database.entities;

import com.library.database.entities.base.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor

@Entity
@Table(name = "admins")
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends User {
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Operator> listOfOperators;
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Book> listOfBooks;
}
