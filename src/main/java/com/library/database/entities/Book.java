package com.library.database.entities;

import com.library.database.enums.BookStatus;
import com.library.database.enums.Genre;
import jakarta.persistence.*;
import lombok.*;

import java.time.Year;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book implements DBEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "number_of_times_used", nullable = false)
    private Integer numberOfTimesUsed;

    @Column(name = "publish_year")
    private Year publishYear;

    @Column(name = "title", length = 64, nullable = false)
    private String title;

    @Column(name = "resume", length = 512)
    private String resume;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Enumerated(EnumType.STRING)
    @Column(name = "genres")
    private Genre genre;

    @Enumerated(EnumType.STRING)
    @Column(name = "book_status", nullable = false)
    private BookStatus bookStatus;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "inventory_id", referencedColumnName = "inventory_id")
    private BookInventory inventory;

    @Override
    public String toString() {

        if(publishYear==null){
            return String.format("Title: %s\nAuthor %s\nGenre: %s\nPublish Year: - \nResume:\n%s",
                    title, author,genre,resume);
        }

        return String.format("Title: %s\nAuthor %s\nGenre: %s\nPublish Year: %s\nResume:\n%s",
                title, author,genre,publishYear,resume);
    }


    public boolean equalsBook(Book book){
        if (this == book) return true;
        if (book == null || getClass() != book.getClass()) return false;
        return title.equals(book.title) && resume.equals(book.resume) && author.equals(book.author) && genre == book.genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id.equals(book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
