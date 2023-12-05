package com.library.database.repositories;

import com.library.database.entities.Operator;
import com.library.database.entities.Reader;
import com.library.database.entities.base.User;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@AllArgsConstructor
public class ReaderRepository extends Repository<Reader> {
    private static final Logger logger = LoggerFactory.getLogger(ReaderRepository.class);

    @Override
    public Optional<Reader> findById(Long id) {
        Reader reader = session.get(Reader.class, id);
        return Optional.ofNullable(reader);
    }

    @Override
    public List<Reader> findAll() {
        return session.createQuery("SELECT r FROM Reader r", Reader.class).getResultList();
    }

    public Reader findByUsername(String username) throws Exception {
        return session.createQuery("Select r from Reader r", Reader.class).stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new Exception("Reader not found"));
    }
}
