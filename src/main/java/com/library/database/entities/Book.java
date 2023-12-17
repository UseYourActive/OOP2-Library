package com.library.database.entities;

import com.library.database.enums.BookStatus;
import com.library.database.enums.Genre;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    @Column(name = "amount_of_copies")
    private static Integer amountOfCopies;

    @Column(name = "number_of_times_used")
    private Integer numberOfTimesUsed;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @Column(name = "title", length = 32, nullable = false)
    private String title;

    @Column(name = "resume", length = 512, nullable = false)
    private String resume;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Column(name = "isbn", length = 16, nullable = false)
    private String isbn;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private Genre genre;

    @Enumerated(EnumType.STRING)
    @Column(name = "book_status", length = 16, nullable = false)
    private BookStatus bookStatus;
}
