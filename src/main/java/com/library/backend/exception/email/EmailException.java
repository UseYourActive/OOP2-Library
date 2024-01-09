/**
 * The {@code EmailException} class is a custom exception that extends {@code LibraryException}. It serves as the base
 * exception for handling email-related errors within the library backend. This exception is part of the library's
 * exception hierarchy and is meant to be subclassed for more specific email-related exceptions.
 *
 * @see LibraryException
 */
package com.library.backend.exception.email;

import com.library.backend.exception.LibraryException;
import lombok.experimental.StandardException;

@StandardException
public class EmailException extends LibraryException {
}
