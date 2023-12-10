package com.library.database.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationType {
    USER_PROFILE_REQUEST("User profile request"),
    BOOK_ARCHIVING("Book archiving"),
    BOOK_OVERDUE("Book overdue");

    private final String displayValue;
}
