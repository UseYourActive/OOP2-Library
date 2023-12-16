package com.library.database.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookStatus {
    AVAILABLE("Available"),
    LENT("Lent"),
    ARCHIVED("Archived"),
    DAMAGED("Damaged"),
    IN_READING_ROOM("In reading room");

    private final String displayValue;
}
