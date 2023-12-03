package com.library.database.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookStatus {
    AVAILABLE("Available"),
    ON_LOAN("On Loan"),
    ARCHIVED("Archived"),
    DAMAGED("Damaged");

    private String displayValue;
}
