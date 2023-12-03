package com.library.database.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    CLIENT("CLIENT"),
    OPERATOR("OPERATOR"),
    ADMIN("ADMIN");

    private String role;
}
