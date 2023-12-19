package com.library.database.entities;

import com.library.database.enums.BookStatus;
import com.library.database.enums.Genre;
import jakarta.persistence.*;
import lombok.*;

import java.time.Year;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

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
    private Integer amountOfCopies;

    @Column(name = "number_of_times_used", nullable = false)
    private Integer numberOfTimesUsed;

    @Column(name = "publish_date")
    private Year publishYear;

    @Column(name = "title", length = 32, nullable = false)
    private String title;

    @Column(name = "resume", length = 512)
    private String resume;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Column(name = "isbn", length = 16, nullable = false)
    private String isbn;

    @Enumerated(EnumType.STRING)
    @Column(name = "genres")
    private Genre genre;

    @Enumerated(EnumType.STRING)
    @Column(name = "book_status", nullable = false)
    private BookStatus bookStatus;

    @Override
    public String toString() {
        return String.format("Id: %d, Title: %s, ISBN: %s, Publish Year: %s",
                id, title, isbn, publishYear);
    }
}
