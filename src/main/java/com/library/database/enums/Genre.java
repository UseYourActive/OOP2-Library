package com.library.database.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * The {@code Genre} enum represents different genres that can be assigned to books in a library system.
 * Each genre has a corresponding value, and the enum provides methods for obtaining the human-readable
 * display value from the enum constants or obtaining an enum constant from a specified value.
 *
 * <p>The enum includes a variety of genres, covering fiction, non-fiction, mystery, science fiction, romance,
 * fantasy, horror, thriller, history, biography, self-help, business, children, poetry, drama, comedy, crime,
 * adventure, philosophy, and travel.</p>
 *
 * <p>Each enum constant has a value that provides a unique identifier for the genre. The {@link #toString()} method
 * returns the value, and the {@link #getValueOf(String)} method can be used to obtain an enum constant based on
 * a specified value.</p>
 *
 * @see lombok.AllArgsConstructor
 * @see lombok.Getter
 */
@Getter
@AllArgsConstructor
public enum Genre {
    /**
     * Fiction genre.
     */
    FICTION("Fiction"),

    /**
     * Non-fiction genre.
     */
    NON_FICTION("Non-fiction"),

    /**
     * Mystery genre.
     */
    MYSTERY("Mystery"),

    /**
     * Science fiction genre.
     */
    SCIENCE_FICTION("Science fiction"),

    /**
     * Romance genre.
     */
    ROMANCE("Romance"),

    /**
     * Fantasy genre.
     */
    FANTASY("Fantasy"),

    /**
     * Horror genre.
     */
    HORROR("Horror"),

    /**
     * Thriller genre.
     */
    THRILLER("Thriller"),

    /**
     * History genre.
     */
    HISTORY("History"),

    /**
     * Biography genre.
     */
    BIOGRAPHY("Biography"),

    /**
     * Self-help genre.
     */
    SELF_HELP("Self help"),

    /**
     * Business genre.
     */
    BUSINESS("Business"),

    /**
     * Children genre.
     */
    CHILDREN("Children"),

    /**
     * Poetry genre.
     */
    POETRY("Poetry"),

    /**
     * Drama genre.
     */
    DRAMA("Drama"),

    /**
     * Comedy genre.
     */
    COMEDY("Comedy"),

    /**
     * Crime genre.
     */
    CRIME("Crime"),

    /**
     * Adventure genre.
     */
    ADVENTURE("Adventure"),

    /**
     * Philosophy genre.
     */
    PHILOSOPHY("Philosophy"),

    /**
     * Travel genre.
     */
    TRAVEL("Travel");

    /**
     * The unique identifier value of the genre.
     */
    private final String value;

    /**
     * Returns the unique identifier value of the genre.
     *
     * @return The value of the genre.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Returns the enum constant associated with the specified value.
     *
     * @param value The value for which to retrieve the enum constant.
     * @return The enum constant associated with the specified value, or {@code null} if not found.
     */
    public static Genre getValueOf(String value) {
        for (Genre genre : Genre.values()) {
            if (Objects.equals(genre.value, value)) {
                return genre;
            }
        }
        return null;
    }
}

