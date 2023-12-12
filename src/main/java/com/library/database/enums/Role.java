package com.library.database.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("Admin"),
    OPERATOR("Operator"),
    READER("Reader");

    private final String role;
}
