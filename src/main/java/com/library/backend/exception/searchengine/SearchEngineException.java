/**
 * The {@code SearchEngineException} class is a base exception that extends {@code LibraryException}. It serves as the
 * root exception for handling errors related to search engine operations within the library backend. This exception is
 * part of the library's exception hierarchy and is meant to be subclassed for more specific search engine-related
 * exceptions.
 *
 * @see LibraryException
 */
package com.library.backend.exception.searchengine;

import com.library.backend.exception.LibraryException;
import lombok.experimental.StandardException;

@StandardException
public class SearchEngineException extends LibraryException {
}
