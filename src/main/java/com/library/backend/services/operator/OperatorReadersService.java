package com.library.backend.services.operator;

import com.google.common.base.Preconditions;
import com.library.backend.engines.ReaderSearchEngine;
import com.library.backend.engines.SearchEngine;
import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.backend.services.Service;
import com.library.database.entities.Reader;
import com.library.database.repositories.ReaderRepository;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * The {@code OperatorReadersService} class provides services for managing operations related to readers
 * performed by operators in the library system.
 *
 * @see Service
 */
public class OperatorReadersService implements Service {
    private final static Logger logger = LoggerFactory.getLogger(OperatorReadersService.class);
    private final ReaderRepository readerRepository;
    @Setter private SearchEngine<Reader> readerSearchEngine;
    @Setter @Getter private double ratingValue;

    /**
     * Constructs an {@code OperatorReadersService} instance with the specified {@code ReaderRepository}.
     *
     * @param readerRepository The repository for managing readers.
     */
    public OperatorReadersService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
        this.readerSearchEngine = new ReaderSearchEngine();
    }

    /**
     * Retrieves a list of all readers in the library.
     *
     * @return A list of readers.
     */
    public List<Reader> getAllReaders() {
        List<Reader> readers = readerRepository.findAll();
        logger.info("Retrieved {} readers from the repository.", readers.size());
        return readers;
    }

    /**
     * Searches for readers based on the specified search string.
     *
     * @param stringToSearch The search string for readers.
     * @return A collection of matching readers.
     * @throws SearchEngineException If an error occurs during the search operation.
     */
    public Collection<Reader> searchReader(String stringToSearch) throws SearchEngineException {
        try {
            return readerSearchEngine.search(readerRepository.findAll(), stringToSearch);
        } catch (SearchEngineException e) {
            logger.error("Failed to search readers", e);
            throw new SearchEngineException("Failed to search readers", e);
        }
    }

    /**
     * Removes the specified reader from the library.
     *
     * @param reader The reader to be removed.
     */
    public void removeReader(Reader reader) {
        try {
            Preconditions.checkNotNull(reader, "Reader cannot be null");
            readerRepository.delete(reader);
            logger.info("Removed reader: {}", reader);
        } catch (Exception e) {
            logger.error("Failed to remove reader", e);
        }
    }
}
