package com.library.database.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookFormStatus {
    IN_USE("In use"),
    RETURNED("Returned"),
    LATE("Late");

    private final String displayValue;
}
