/**
 * The {@code UserExistException} class is an exception that extends {@code LibraryException}. It is thrown when an
 * attempt is made to create a user account with a username or identifier that already exists in the system.
 * <p>
 * This exception indicates that the user already exists, and developers should handle it to ensure proper error
 * handling, provide appropriate feedback to users, and avoid duplicate user accounts in the system.
 *
 * @see LibraryException
 */
package com.library.backend.exception;

import lombok.experimental.StandardException;

@StandardException
public class UserExistException extends LibraryException {
}
