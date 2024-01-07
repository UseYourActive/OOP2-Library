package com.library.utils.validators;

/**
 * The {@code Validator} interface defines a contract for classes that perform
 * validation on a given value. Implementing classes should provide an implementation
 * for the {@link #isValid(String)} method to determine whether a value is valid
 * based on specific criteria.
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * // Create a custom implementation of Validator for a specific validation criteria
 * public class CustomValidator implements Validator {
 *     {@literal @}Override
 *     public boolean isValid(String value) {
 *         // Implement validation logic based on specific criteria
 *         // Return true if the value is valid, false otherwise
 *         // ...
 *     }
 * }
 * }
 * </pre>
 *
 * @see StrongPasswordValidator
 */
public interface Validator {
    /**
     * Checks whether the provided value is valid based on specific criteria.
     *
     * @param value The value to be validated.
     * @return {@code true} if the value is valid; {@code false} otherwise.
     */
    boolean isValid(String value);
}

