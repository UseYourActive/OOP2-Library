package com.project.database.entities;

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
