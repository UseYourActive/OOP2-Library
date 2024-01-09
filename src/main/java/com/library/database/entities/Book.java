package com.library.database.entities;

import com.library.database.enums.BookStatus;
import com.library.database.enums.Genre;
import jakarta.persistence.*;
import lombok.*;

import java.time.Year;
import java.util.Objects;

/**
 * The {@code Book} class represents an entity that encapsulates information about a book
 * in the library management system. It is annotated with JPA annotations to enable
 * object-relational mapping with a corresponding database table.
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * // Create a new Book instance
 * Book book = Book.builder()
 *                .title("The Great Gatsby")
 *                .author(author)
 *                .genre(Genre.FICTION)
 *                .publishYear(Year.of(1925))
 *                .resume("A classic novel by F. Scott Fitzgerald.")
 *                .bookStatus(BookStatus.AVAILABLE)
 *                .build();
 * }
 * </pre>
 *
 * @see DBEntity
 * @see jakarta.persistence.Entity
 * @see jakarta.persistence.Table
 * @see jakarta.persistence.Id
 * @see jakarta.persistence.GeneratedValue
 * @see jakarta.persistence.Column
 * @see jakarta.persistence.ManyToOne
 * @see jakarta.persistence.JoinColumn
 * @see jakarta.persistence.Enumerated
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book implements DBEntity {

    /**
     * The unique identifier for the book.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    /**
     * The number of times the book has been used.
     */
    @Column(name = "number_of_times_used", nullable = false)
    private Integer numberOfTimesUsed;

    /**
     * The year when the book was published.
     */
    @Column(name = "publish_year")
    private Year publishYear;

    /**
     * The title of the book.
     */
    @Column(name = "title", length = 64, nullable = false)
    private String title;

    /**
     * The summary or resume of the book.
     */
    @Column(name = "resume", length = 512)
    private String resume;

    /**
     * The author of the book.
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    /**
     * The genre of the book.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "genres")
    private Genre genre;

    /**
     * The current status of the book.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "book_status", nullable = false)
    private BookStatus bookStatus;

    /**
     * The previous status of the book.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "previous_book_status")
    private BookStatus previousBookStatus;

    /**
     * The inventory associated with the book.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "inventory_id", referencedColumnName = "inventory_id")
    private BookInventory inventory;

    /**
     * Cloning constructor for creating a copy of the book.
     *
     * @param book The original book to clone.
     */
    public Book(Book book) {
        this.publishYear = book.publishYear;
        this.title = book.title;
        this.resume = book.resume;
        this.author = book.author;
        this.genre = book.genre;
        this.inventory = book.inventory;
    }

    /**
     * Returns a string representation of the book.
     *
     * @return A formatted string containing book details.
     */
    @Override
    public String toString() {
        String publishYearString = (publishYear == null) ? "-" : publishYear.toString();
        return String.format("Title: %s\nAuthor: %s\nGenre: %s\nPublish Year: %s\t\tTimes Used: %d\nResume:\n%s",
                title, author, genre, publishYearString, numberOfTimesUsed, resume);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o The object to compare with.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id.equals(book.id);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return A hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Compares two books based on their title, resume, author, and genre.
     *
     * @param book The book to compare with.
     * @return {@code true} if the books are equal, {@code false} otherwise.
     */
    public boolean equalsBook(Book book) {
        if (this == book) return true;
        if (book == null || getClass() != book.getClass()) return false;
        return title.equals(book.title) && resume.equals(book.resume) && author.equals(book.author) && genre == book.genre;
    }

    /**
     * Updates the previous book status with the current book status.
     */
    public void updatePreviousBookStatus() {
        previousBookStatus = bookStatus;
    }
}
