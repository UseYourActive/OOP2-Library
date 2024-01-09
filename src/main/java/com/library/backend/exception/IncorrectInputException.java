/**
 * The {@code IncorrectInputException} class represents exceptions that occur when there is incorrect input in the
 * library's backend operations. It extends the base {@code LibraryException} and provides more specific information about
 * issues related to incorrect input. This exception is part of the library's exception hierarchy and can be caught to
 * handle errors related to incorrect input separately from other library exceptions.
 *
 * @see LibraryException
 */
package com.library.backend.exception;

import lombok.experimental.StandardException;

@StandardException
public class IncorrectInputException extends LibraryException {
}
