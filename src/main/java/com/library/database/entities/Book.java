package com.library.database.entities;

import com.library.database.enums.BookStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "inventory_number", unique = true, nullable = false)
    private String inventoryNumber;

    @Column(name = "title", length = 32, nullable = false)
    private String title;

    @Column(name = "resume", length = 512, nullable = false)
    private String resume;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Column(name = "isbn", length = 16, nullable = false)
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    @Enumerated(EnumType.STRING)
    @Column(name = "book_status", length = 16, nullable = false)
    private BookStatus bookStatus;
}
