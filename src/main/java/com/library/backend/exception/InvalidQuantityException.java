/**
 * The {@code InvalidQuantityException} class represents exceptions that occur when there is an invalid quantity
 * involved in the library's backend operations. It extends the base {@code LibraryException} and provides more specific
 * information about issues related to invalid quantities. This exception is part of the library's exception hierarchy
 * and can be caught to handle errors related to invalid quantities separately from other library exceptions.
 *
 * @see LibraryException
 */
package com.library.backend.exception;

import lombok.experimental.StandardException;

@StandardException
public class InvalidQuantityException extends LibraryException {
}
