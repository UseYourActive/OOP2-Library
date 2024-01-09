package com.library.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

/**
 * The {@code Author} class represents an entity that encapsulates information about an author
 * in the library management system. It is annotated with JPA annotations to enable
 * object-relational mapping with a corresponding database table.
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * // Create a new Author instance
 * Author author = Author.builder()
 *                      .name("John Doe")
 *                      .books(List.of(book1, book2))
 *                      .build();
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
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
public class Author implements DBEntity {

    /**
     * The unique identifier for the author.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long id;

    /**
     * The name of the author.
     */
    @Column(name = "name", length = 32, nullable = false, unique = true)
    private String name;

    /**
     * The list of books associated with the author.
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;

    /**
     * Returns a string representation of the author.
     *
     * @return The name of the author.
     */
    @Override
    public String toString() {
        return name;
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
        Author author = (Author) o;
        return id.equals(author.id);
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
}
