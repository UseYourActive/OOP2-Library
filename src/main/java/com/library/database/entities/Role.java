package com.library.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@AllArgsConstructor
public enum Role {
    CLIENT("CLIENT"),
    OPERATOR("OPERATOR"),
    ADMIN("ADMIN");

    private String role;
}
