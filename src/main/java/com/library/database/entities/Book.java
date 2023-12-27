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
@Table(name = "books", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title", "author_id", "publish_date"})
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "genres")
    private Genre genre;

    @Enumerated(EnumType.STRING)
    @Column(name = "book_status", nullable = false)
    private BookStatus bookStatus;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Reader lentToReader;

    @Override
    public String toString() {

        if(publishYear==null){
            return String.format("Title: %s\nAuthor %s\nGenre: %s\nPublish Year: - \nStatus: %s\nResume:\n%s",
                    title, author,genre,bookStatus,resume);
        }

        return String.format("Title: %s\nAuthor %s\nGenre: %s\nPublish Year: %s\nStatus: %s\nResume:\n%s",
                title, author,genre,publishYear,bookStatus,resume);
    }
}
