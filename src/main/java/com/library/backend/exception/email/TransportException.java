/**
 * The {@code TransportException} class is a specific type of {@code EmailException} that represents errors related to
 * email transport mechanisms. It extends the base {@code EmailException} and provides more specific information about
 * email transport-related errors.
 *
 * <p>This exception is part of the library's exception hierarchy and can be caught to handle transport-related email
 * errors separately from other email-related issues.</p>
 *
 * @see EmailException
 */
package com.library.backend.exception.email;

import lombok.experimental.StandardException;

@StandardException
public class TransportException extends EmailException {
}
