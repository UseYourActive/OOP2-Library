package com.library.database.repositories;

import com.library.database.entities.EventNotification;
import com.library.database.entities.Genre;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class GenreRepository extends Repository<Genre> {
    private static final Logger logger = LoggerFactory.getLogger(GenreRepository.class);

    @Override
    public Optional<Genre> findById(Long id) {
        try {
            Genre genre = session.get(Genre.class, id);
            return Optional.ofNullable(genre);
        } catch (Exception e) {
            logger.error("Error finding genre by ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public List<Genre> findAll() {
        try {
            return session.createQuery("SELECT g FROM Genre g", Genre.class).getResultList();
        } catch (Exception e) {
            logger.error("Error retrieving all genres", e);
            throw e;
        }
    }

    @Override
    public Genre getById(Long id) {
        logger.info("Successfully found genre with id: {}", id);
        return session.get(Genre.class, id);
    }

    public Optional<Genre> findGenreByName(String name) {
        try {
            return session.createQuery("SELECT g FROM Genre g WHERE g.name = :name", Genre.class)
                    .setParameter("name", name)
                    .getResultStream()
                    .findFirst();
        } catch (Exception e) {
            logger.error("Error finding genre by name: {}", name, e);
            throw e;
        }
    }
}
