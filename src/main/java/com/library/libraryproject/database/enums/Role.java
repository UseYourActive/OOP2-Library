package com.library.libraryproject.database.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    CLIENT("Client"),
    OPERATOR("Operator"),
    ADMIN("Admin");

    private String role;
}
