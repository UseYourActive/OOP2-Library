package com.library.database.entities;

import com.library.database.enums.BookFormStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_forms")
public class BookForm implements DBEntity{
    @Id
    @Column(name = "form_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany()
    private List<Book> books;

    @OneToOne()
    private Reader reader;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookFormStatus status;

    @Column(name = "date_of_creation",unique = true)
    private LocalDate dateOfCreation;
}
