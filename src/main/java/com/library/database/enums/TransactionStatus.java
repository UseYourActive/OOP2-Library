package com.library.database.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionStatus {
    PENDING("Pending"),
    RETURNED("Returned"),
    OVERDUE("Overdue");

    private final String displayValue;
}
