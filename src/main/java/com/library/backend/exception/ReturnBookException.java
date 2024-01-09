/**
 * The {@code ReturnBookException} class is an exception that extends {@code LibraryException}. It is thrown when an
 * error occurs during the return of a book in the library backend.
 * <p>
 * This exception indicates that there was an issue while processing the return of a book, and developers should handle
 * it to ensure proper error handling and provide appropriate feedback to users or log the issue for further
 * investigation.
 *
 * @see LibraryException
 */
package com.library.backend.exception;

import lombok.experimental.StandardException;

@StandardException
public class ReturnBookException extends LibraryException {
}
