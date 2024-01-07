package com.library.backend.validators;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * The {@code StrongPasswordValidator} class implements the {@link Validator} interface
 * to check if a given password meets strong password criteria. The criteria include:
 * <ul>
 *     <li>At least one digit [0-9]</li>
 *     <li>At least one lowercase character [a-z]</li>
 *     <li>At least one uppercase character [A-Z]</li>
 *     <li>No whitespace allowed</li>
 *     <li>Minimum length of 6 characters</li>
 *     <li>Maximum length of 20 characters</li> <!-- Added maximum length -->
 * </ul>
 * This class uses a regular expression pattern to perform the validation.
 *
 * @see Validator
 */
public final class StrongPasswordValidator implements Validator {
    // Updated regular expression pattern with a maximum length of 20 characters
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,20}$";

    /**
     * Checks if the provided password meets the strong password criteria.
     *
     * @param password The password to be validated.
     * @return {@code true} if the password meets the criteria; {@code false} otherwise.
     * @throws PatternSyntaxException If the regular expression pattern is invalid.
     */
    public boolean isValid(String password) throws PatternSyntaxException {
        return password != null && Pattern.matches(PASSWORD_PATTERN, password);
    }
}
