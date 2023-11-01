package com.library.entities;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    ADMIN("Admin"),
    CLIENT("Client"),
    OPERATOR("Operator");

    private final String value;

}
