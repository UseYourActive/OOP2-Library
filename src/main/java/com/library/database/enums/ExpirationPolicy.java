package com.library.database.enums;

import lombok.Getter;


public enum ExpirationPolicy {
    HOURS_24(24),
    MONTH(1);


    ExpirationPolicy(int value) {
        this.value = value;
    }

    @Getter
    private final int value;
}
