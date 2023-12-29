package com.library.database.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The {@code BookStatus} enum represents the possible statuses of a book in a library system.
 * Each status has a corresponding display value.
 *
 * <p>The enum includes the following statuses:</p>
 * <ul>
 *     <li>{@link #AVAILABLE}: The book is available for borrowing.</li>
 *     <li>{@link #LENT}: The book is currently lent to a reader.</li>
 *     <li>{@link #ARCHIVED}: The book is archived and not available for regular borrowing.</li>
 *     <li>{@link #DAMAGED}: The book is damaged and may not be available for borrowing.</li>
 *     <li>{@link #IN_READING_ROOM}: The book is available for reading within the library's reading room.</li>
 * </ul>
 *
 * <p>Each enum constant has a display value that provides a human-readable representation of the status.</p>
 *
 * @see lombok.AllArgsConstructor
 * @see lombok.Getter
 */
@Getter
@AllArgsConstructor
public enum BookStatus {
    /**
     * The book is available for borrowing.
     */
    AVAILABLE("Available"),

    /**
     * The book is currently lent to a reader.
     */
    LENT("Lent"),

    /**
     * The book is archived and not available for regular borrowing.
     */
    ARCHIVED("Archived"),

    /**
     * The book is damaged and may not be available for borrowing.
     */
    DAMAGED("Damaged"),

    /**
     * The book is available for reading within the library's reading room.
     */
    IN_READING_ROOM("In reading room");

    /**
     * The human-readable display value of the book status.
     */
    private final String displayValue;
}

