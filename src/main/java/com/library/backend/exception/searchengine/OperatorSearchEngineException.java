/**
 * The {@code OperatorSearchEngineException} class is a specific type of {@code SearchEngineException} that represents
 * errors related to operator search engine operations. It extends the base {@code SearchEngineException} and provides
 * more specific information about issues encountered during operator searches.
 *
 * <p>This exception is part of the library's exception hierarchy and can be caught to handle operator search engine
 * errors separately from other search engine-related issues.</p>
 *
 * @see SearchEngineException
 */
package com.library.backend.exception.searchengine;

import lombok.experimental.StandardException;

@StandardException
public class OperatorSearchEngineException extends SearchEngineException {
}
