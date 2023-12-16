package com.library.database.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FormStatus {
    IN_USE("In use"),
    RETURNED("Returned"),
    LATE("Late");

    private final String displayValue;
}
