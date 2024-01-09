/**
 * The {@code AdminNotFoundException} class is a specific type of {@code UserNotFoundException} that represents errors
 * related to the absence or non-existence of an administrator user in the library system. It extends the base
 * {@code UserNotFoundException} and provides more specific information about issues encountered when trying to find an
 * administrator user.
 *
 * <p>This exception is part of the library's exception hierarchy and can be caught to handle administrator user
 * not found errors separately from other user-related issues.</p>
 *
 * @see UserNotFoundException
 */
package com.library.backend.exception.users;

import lombok.experimental.StandardException;

@StandardException
public class AdminNotFoundException extends UserNotFoundException {
}
