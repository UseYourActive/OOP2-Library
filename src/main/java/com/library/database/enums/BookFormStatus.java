package com.library.database.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The {@code BookFormStatus} enum represents the possible statuses of a book request form in a library system.
 * Each status has a corresponding display value.
 *
 * <p>The enum includes the following statuses:</p>
 * <ul>
 *     <li>{@link #IN_USE}: The book request form is currently in use.</li>
 *     <li>{@link #RETURNED}: The book request form has been returned.</li>
 *     <li>{@link #LATE}: The book request form is late.</li>
 * </ul>
 *
 * <p>Each enum constant has a display value that provides a human-readable representation of the status.</p>
 *
 * @see lombok.AllArgsConstructor
 * @see lombok.Getter
 */
@Getter
@AllArgsConstructor
public enum BookFormStatus {
    /**
     * The book request form is currently in use.
     */
    IN_USE("In use"),

    /**
     * The book request form has been returned.
     */
    RETURNED("Returned"),

    /**
     * The book request form is late.
     */
    LATE("Late");

    /**
     * The human-readable display value of the book request form status.
     */
    private final String displayValue;
}
