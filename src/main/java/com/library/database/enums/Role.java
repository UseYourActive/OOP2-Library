package com.library.database.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The {@code Role} enum represents different roles that users can have in a library system.
 * Each role has a corresponding display value, indicating the level of access or authority
 * associated with that role. The enum provides two roles: "Admin" and "Operator."
 *
 * @see lombok.Getter
 * @see lombok.AllArgsConstructor
 */
@Getter
@AllArgsConstructor
public enum Role {
    /**
     * Administrator role with full access and authority.
     */
    ADMIN("Admin"),

    /**
     * Operator role with limited access and authority.
     */
    OPERATOR("Operator");

    /**
     * The display value of the role.
     */
    private final String role;
}
