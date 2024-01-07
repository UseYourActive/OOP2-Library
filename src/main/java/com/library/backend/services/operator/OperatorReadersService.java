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

public class OperatorReadersService implements Service {
    private final static Logger logger = LoggerFactory.getLogger(OperatorReadersService.class);
    private final ReaderRepository readerRepository;
    private SearchEngine<Reader> readerSearchEngine;
    @Setter
    @Getter
    private double ratingValue;

    public OperatorReadersService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
        this.readerSearchEngine = new ReaderSearchEngine();
    }

    public List<Reader> getAllReaders() {
        try {
            List<Reader> readers = readerRepository.findAll();
            logger.info("Retrieved {} readers from the repository.", readers.size());
            return readers;
        } catch (Exception e) {
            logger.error("Failed to retrieve readers", e);
            throw new RuntimeException("Failed to retrieve readers", e);
        }
    }

    public Collection<Reader> searchReader(String stringToSearch) throws SearchEngineException {
        try {
            return readerSearchEngine.search(readerRepository.findAll(), stringToSearch);
        } catch (SearchEngineException e) {
            logger.error("Failed to search readers", e);
            throw new RuntimeException("Failed to search readers", e);
        }
    }

    public void removeReader(Reader reader) {
        try {
            Preconditions.checkNotNull(reader, "Reader cannot be null");
            readerRepository.delete(reader);
            logger.info("Removed reader: {}", reader);
        } catch (Exception e) {
            logger.error("Failed to remove reader", e);
            throw new RuntimeException("Failed to remove reader", e);
        }
    }
}
