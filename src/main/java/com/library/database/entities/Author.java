package com.library.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @Column(name = "author_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", length = 32, nullable = false)
    private String name;

    @Column(name = "date_of_birth", nullable = false)
    private Timestamp dateOfBirth;

    @Column(name = "country", length = 16, nullable = false)
    private String country;

    @OneToMany(mappedBy = "author", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Book> books;
}
