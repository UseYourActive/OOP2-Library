/**
 * The {@code NonExistentServiceException} class represents exceptions that occur when attempting to access a
 * non-existent service in the library's backend operations. It extends the base {@code LibraryException} and provides
 * more specific information about issues related to non-existent services. This exception is part of the library's
 * exception hierarchy and can be caught to handle errors related to non-existent services separately from other library
 * exceptions.
 *
 * @see LibraryException
 */
package com.library.backend.exception;

import lombok.experimental.StandardException;

@StandardException
public class NonExistentServiceException extends LibraryException {
}
