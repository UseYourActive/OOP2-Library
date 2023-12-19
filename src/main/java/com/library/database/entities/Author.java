package com.library.database.entities;

import jakarta.persistence.*;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long id;

    @Column(name = "name", length = 32, nullable = false)
    private String name;

    @OneToMany(mappedBy = "author", orphanRemoval = true)
    private List<Book> books;

    @Override
    public String toString() {
        return name;
    }
}
