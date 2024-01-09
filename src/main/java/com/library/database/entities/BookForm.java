package com.library.database.entities;

import com.library.database.enums.BookFormStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The {@code BookForm} class represents an entity that encapsulates information about
 * a form created by a reader to borrow or return books. It is annotated with JPA
 * annotations to enable object-relational mapping with a corresponding database table.
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * // Create a new BookForm instance
 * BookForm bookForm = BookForm.builder()
 *                              .books(books)
 *                              .reader(reader)
 *                              .status(BookFormStatus.PENDING)
 *                              .dateOfCreation(LocalDateTime.now())
 *                              .expirationDate(LocalDateTime.now().plusDays(14))
 *                              .build();
 * }
 * </pre>
 *
 * @see DBEntity
 * @see jakarta.persistence.Entity
 * @see jakarta.persistence.Table
 * @see jakarta.persistence.Id
 * @see jakarta.persistence.GeneratedValue
 * @see jakarta.persistence.Column
 * @see jakarta.persistence.ManyToMany
 * @see jakarta.persistence.JoinTable
 * @see jakarta.persistence.JoinColumn
 * @see jakarta.persistence.ManyToOne
 * @see jakarta.persistence.Enumerated
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_forms")
public class BookForm implements DBEntity {

    /**
     * The unique identifier for the book form.
     */
    @Id
    @Column(name = "form_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The list of books associated with the form.
     */
    @ManyToMany
    @JoinTable(
            name = "book_forms_books",
            joinColumns = @JoinColumn(name = "form_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books;

    /**
     * The reader associated with the form.
     */
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Reader reader;

    /**
     * The status of the book form (e.g., PENDING, IN_USE, RETURNED).
     */
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookFormStatus status;

    /**
     * The date and time when the form was created.
     */
    @Column(name = "date_of_creation", nullable = false, unique = true)
    private LocalDateTime dateOfCreation;

    /**
     * The date and time when the form expires.
     */
    @Column(name = "date_of_expiration", nullable = false)
    private LocalDateTime expirationDate;

    /**
     * Returns a string representation of the book form, including the date of creation
     * and the status.
     *
     * @return A formatted string containing book form details.
     */
    @Override
    public String toString() {
        return dateOfCreation.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) + " " + status;
    }

    /**
     * Checks if the book form is overdue based on the expiration date.
     *
     * @return {@code true} if the form is overdue, {@code false} otherwise.
     */
    public boolean isOverdue() {
        return LocalDateTime.now().isAfter(expirationDate);
    }

    /**
     * Checks if the book form is currently present or in use.
     *
     * @return {@code true} if the form is in use, {@code false} otherwise.
     */
    public boolean isPresent() {
        return this.status.equals(BookFormStatus.IN_USE);
    }
}
