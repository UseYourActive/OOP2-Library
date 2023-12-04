package com.library.database.repositories;

import com.library.database.entities.Reader;
import com.library.database.entities.base.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class ReaderRepository extends Repository<Reader> {
    private static final Logger logger = LoggerFactory.getLogger(ReaderRepository.class);

    public ReaderRepository() {
        super();
    }

    @Override
    public Reader get(Long id) {
        return session.find(Reader.class, id);
    }

    @Override
    public Stream<Reader> getAll() {
        return session.createQuery("SELECT r FROM Reader r", Reader.class).getResultStream();
    }

    public Reader findByUsername(String username) throws Exception {
        return session.createQuery("Select r from Reader r", Reader.class).stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new Exception("Reader not found"));
    }
}
