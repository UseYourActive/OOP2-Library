package com.library.database.entities;

import com.library.database.enums.BookStatus;
import com.library.database.enums.Genre;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.lang.reflect.Field;
import java.time.Year;
import java.util.*;

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

    @Column(name = "title", length = 64, nullable = false)
    private String title;

    @Column(name = "resume", length = 512)
    private String resume;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @NaturalId
    @Column(name = "isbn", length = 16, nullable = false,unique = true)
    private String isbn;

    @Enumerated(EnumType.STRING)
    @Column(name = "genres")
    private Genre genre;

    @Enumerated(EnumType.STRING)
    @Column(name = "book_status", nullable = false)
    private BookStatus bookStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {

        if(publishYear==null){
            return String.format("Title: %s\nAuthor %s\nGenre: %s\nPublish Year: - \nAvailability: %d\nISBN: %s\nResume:\n%s",
                    title, author,genre,amountOfCopies, isbn,resume);
        }

        return String.format("Title: %s\nAuthor %s\nGenre: %s\nPublish Year: %s\nAvailability: %d\nISBN: %s\nResume:\n%s",
                title, author,genre,publishYear,amountOfCopies, isbn,resume);
    }
}
