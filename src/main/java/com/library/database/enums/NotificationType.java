package com.library.database.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The {@code NotificationType} enum represents the different types of notifications in a library system.
 * Each type has a corresponding display value.
 *
 * <p>The enum includes the following notification types:</p>
 * <ul>
 *     <li>{@link #USER_PROFILE_REQUEST}: Notification related to a user profile request.</li>
 *     <li>{@link #BOOK_ARCHIVING}: Notification related to the archiving of a book.</li>
 *     <li>{@link #BOOK_OVERDUE}: Notification related to a book that is overdue.</li>
 * </ul>
 *
 * <p>Each enum constant has a display value that provides a human-readable representation of the notification type.</p>
 *
 * @see lombok.AllArgsConstructor
 * @see lombok.Getter
 */
@Getter
@AllArgsConstructor
public enum NotificationType {
    /**
     * Notification related to a user profile request.
     */
    USER_PROFILE_REQUEST("User profile request"),

    /**
     * Notification related to the archiving of a book.
     */
    BOOK_ARCHIVING("Book archiving"),

    /**
     * Notification related to a book that is overdue.
     */
    BOOK_OVERDUE("Book overdue");

    /**
     * The human-readable display value of the notification type.
     */
    private final String displayValue;
}

