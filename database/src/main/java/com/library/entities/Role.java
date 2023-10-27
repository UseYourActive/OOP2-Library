package com.library.entities;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("Admin"),
    CLIENT("Client"),
    OPERATOR("Operator");

    private final String value;

    Role(String value) {
        this.value = value;
    }
}
