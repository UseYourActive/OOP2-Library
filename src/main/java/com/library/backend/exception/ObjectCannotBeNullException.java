/**
 * The {@code ObjectCannotBeNullException} class is an exception that extends {@code LibraryException}. It is thrown when
 * an operation in the library backend encounters a null object that is expected to have a non-null value.
 * <p>
 * This exception indicates that a required object, which should not be null, is found to be null during the execution
 * of a specific operation. Developers should handle this exception to ensure proper error handling and provide
 * appropriate feedback to users or log the issue for further investigation.
 *
 * @see LibraryException
 */
package com.library.backend.exception;

import lombok.experimental.StandardException;

@StandardException
public class ObjectCannotBeNullException extends LibraryException {
}
