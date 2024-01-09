package com.library.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

/**
 * The {@code BookInventory} class represents an entity that contains information about
 * a collection of books sharing identical title, author, and genre. It is annotated with
 * JPA annotations to enable object-relational mapping with a corresponding database table.
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * // Create a new BookInventory instance
 * BookInventory bookInventory = BookInventory.builder()
 *                                          .bookList(bookList)
 *                                          .representativeBook(representativeBook)
 *                                          .build();
 * }
 * </pre>
 *
 * @see DBEntity
 * @see jakarta.persistence.Entity
 * @see jakarta.persistence.Table
 * @see jakarta.persistence.Id
 * @see jakarta.persistence.GeneratedValue
 * @see jakarta.persistence.Column
 * @see jakarta.persistence.OneToMany
 * @see jakarta.persistence.CascadeType
 * @see jakarta.persistence.FetchType
 * @see jakarta.persistence.OneToOne
 * @see jakarta.persistence.JoinColumn
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_inventories")
public class BookInventory implements DBEntity {

    /**
     * The unique identifier for the book inventory.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long id;

    /**
     * The list of books associated with the inventory. Represents multiple books with
     * identical title, author, and genre.
     */
    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Book> bookList;

    /**
     * The representative book that represents one book from the bookList.
     */
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book representativeBook;

    /**
     * Returns a string representation of the book inventory, including the title,
     * author, genre, quantity, publish year, and resume.
     *
     * @return A formatted string containing book inventory details.
     */
    @Override
    public String toString() {
        if (representativeBook.getPublishYear() == null) {
            return String.format("Title: %s\nAuthor %s\nGenre: %s\t\tQuantity: %d\nPublish Year: - \nResume:\n%s",
                    representativeBook.getTitle(), representativeBook.getAuthor(), representativeBook.getGenre(), bookList.size(), representativeBook.getResume());
        }

        return String.format("Title: %s\nAuthor %s\nGenre: %s\t\tQuantity: %d\nPublish Year: %s\nResume:\n%s",
                representativeBook.getTitle(), representativeBook.getAuthor(), representativeBook.getGenre(), bookList.size(), representativeBook.getPublishYear(), representativeBook.getResume());
    }

    /**
     * Checks if the given object is equal to this book inventory based on the unique identifier.
     *
     * @param o The object to compare with this book inventory.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookInventory that = (BookInventory) o;
        return id.equals(that.id);
    }

    /**
     * Returns the hash code value for this book inventory based on the unique identifier.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Adds a book to the book list associated with the inventory.
     *
     * @param book The book to add to the inventory.
     * @return {@code true} if the book is added successfully, {@code false} otherwise.
     */
    public boolean addBook(Book book) {
        return bookList.add(book);
    }
}
