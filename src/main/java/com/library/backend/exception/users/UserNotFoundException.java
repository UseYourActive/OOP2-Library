/**
 * The {@code UserNotFoundException} class is a base exception that extends {@code LibraryException}. It serves as the
 * root exception for handling errors related to user operations within the library backend. This exception is meant to
 * be subclassed for more specific user-related exceptions, such as {@code AdminNotFoundException},
 * {@code OperatorNotFoundException}, and {@code ReaderNotFoundException}.
 *
 * @see LibraryException
 */
package com.library.backend.exception.users;

import com.library.backend.exception.LibraryException;
import lombok.experimental.StandardException;

@StandardException
public class UserNotFoundException extends LibraryException {
}
