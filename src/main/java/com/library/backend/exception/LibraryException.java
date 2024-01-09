/**
 * The {@code LibraryException} class is a base exception that extends {@code Exception}. It serves as the root
 * exception for handling errors within the library backend. This exception is meant to be subclassed for more specific
 * library-related exceptions, such as {@code IncorrectInputException}, {@code InvalidQuantityException}, and
 * {@code NonExistentServiceException}.
 * <p>
 * This class is part of the library's exception hierarchy and provides a common base for exceptions that can occur
 * during the execution of library backend operations. Developers are encouraged to create specific exception classes
 * by extending this base class to capture more detailed information about specific types of errors.
 *
 * @see Exception
 * @see IncorrectInputException
 * @see InvalidQuantityException
 * @see NonExistentServiceException
 */
package com.library.backend.exception;

import lombok.experimental.StandardException;

@StandardException
public class LibraryException extends Exception {
}
