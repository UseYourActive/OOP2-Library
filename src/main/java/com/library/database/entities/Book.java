package com.library.database.entities;

import com.library.database.enums.BookStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "inventory_number", length = 13, unique = true, nullable = false)
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

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;
}
