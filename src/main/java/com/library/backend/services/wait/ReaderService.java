package com.library.backend.services.wait;

import com.google.common.base.Preconditions;
import com.library.backend.services.Service;
import com.library.database.entities.Reader;
import com.library.database.repositories.ReaderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ReaderService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(ReaderService.class);
    private final ReaderRepository readerRepository;

    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = Preconditions.checkNotNull(readerRepository, "ReaderRepository cannot be null");
    }

    public void saveReader(Reader reader) {
        readerRepository.save(reader);
    }

    public List<Reader> getAllReaders() {
        List<Reader> readers = readerRepository.findAll();
        logger.info("Retrieved {} readers from the repository.", readers.size());
        return readers;
    }

    public void createReader(Reader reader) {
        Preconditions.checkNotNull(reader, "Reader cannot be null");
        readerRepository.save(reader);
        logger.info("Created a new reader: {}", reader);
    }

    public void removeReader(Reader reader) {
        Preconditions.checkNotNull(reader, "Reader cannot be null");
        readerRepository.delete(reader);
        logger.info("Removed reader: {}", reader);
    }
}
