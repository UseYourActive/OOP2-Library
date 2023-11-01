package com.library.entities;

public enum Role {
    ADMIN("Admin"),
    CLIENT("Client"),
    OPERATOR("Operator");

    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
