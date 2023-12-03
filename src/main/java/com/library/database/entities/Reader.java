package com.library.database.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "readers")
@Builder
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Reader extends Client {
    //private List<BorrowedBooks> listOfBorrowedBooks;
}
