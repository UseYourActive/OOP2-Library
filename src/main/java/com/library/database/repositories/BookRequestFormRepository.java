package com.library.database.repositories;

import com.library.database.entities.BookRequestForm;
import com.library.database.entities.Genre;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class BookRequestFormRepository extends Repository<BookRequestForm>{
    private static final Logger logger = LoggerFactory.getLogger(BookRequestForm.class);

    @Override
    public Optional<BookRequestForm> findById(Long id) {
        try {
            BookRequestForm bookRequestForm = session.get(BookRequestForm.class, id);
            return Optional.ofNullable(bookRequestForm);
        } catch (Exception e) {
            logger.error("Error finding genre by ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public List<BookRequestForm> findAll() {
        try {
            return session.createQuery("SELECT b FROM BookRequestForm b", BookRequestForm.class).getResultList();
        } catch (Exception e) {
            logger.error("Error retrieving all genres", e);
            throw e;
        }
    }

    public Optional<BookRequestForm> findGenreByName(String name) {
        try {
            return session.createQuery("SELECT g FROM BookRequestForm b WHERE b.name = :name", BookRequestForm.class)
                    .setParameter("name", name)
                    .getResultStream()
                    .findFirst();
        } catch (Exception e) {
            logger.error("Error finding genre by name: {}", name, e);
            throw e;
        }
    }
}
