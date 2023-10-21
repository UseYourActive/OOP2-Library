package com.library.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "books")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "resume", nullable = false)
    private String resume;

    @ManyToMany
    @Column(name = "authors", nullable = false)
    private List<Author> author;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "genre", nullable = false)
    private Genre genre;
}
