/**
 * The {@code ReaderException} class is an exception that extends {@code LibraryException}. It is used to represent
 * general errors related to reader operations in the library backend.
 * <p>
 * This exception is thrown when an unexpected error occurs while performing operations related to readers. Developers
 * should handle this exception to ensure proper error handling and provide appropriate feedback to users or log the
 * issue for further investigation.
 *
 * @see LibraryException
 */
package com.library.backend.exception;

import lombok.experimental.StandardException;

@StandardException
public class ReaderException extends LibraryException {
}
