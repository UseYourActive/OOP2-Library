package com.library.database.repositories;

import com.library.database.entities.Genre;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class GenreRepository extends Repository<Genre> {
    private static final Logger logger = LoggerFactory.getLogger(GenreRepository.class);
    private static volatile GenreRepository instance;

    private GenreRepository() {
    }

    public static GenreRepository getInstance() {
        if (instance == null) {
            synchronized (GenreRepository.class) {
                if (instance == null) {
                    instance = new GenreRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public Optional<Genre> findById(Long id) throws HibernateException {
        try {
            Genre genre = session.get(Genre.class, id);
            if (genre != null) {
                logger.info("Successfully found genre with id: {}", id);
            } else {
                logger.info("No genre found with id: {}", id);
                // maybe throw an exception?
            }
            return Optional.ofNullable(genre);
        } catch (HibernateException e) {
            logger.error("Error finding genre by ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public List<Genre> findAll() throws HibernateException {
        try {
            return session.createQuery("SELECT g FROM Genre g", Genre.class).getResultList();
        } catch (HibernateException e) {
            logger.error("Error retrieving all genres", e);
            throw e;
        }
    }

    @Override
    public Genre getById(Long id) throws HibernateException {
        Genre genre = session.get(Genre.class, id);
        if (genre != null) {
            logger.info("Successfully found genre with id: {}", id);
        }
        return genre;
    }

    private <T> Optional<T> executeQuery(String query, String paramName, String paramValue, Class<T> resultType) throws HibernateException {
        try {
            return session.createQuery(query, resultType)
                    .setParameter(paramName, paramValue)
                    .getResultStream()
                    .findFirst();
        } catch (HibernateException e) {
            logger.error("Error executing query: {}", query, e);
            throw e;
        }
    }

    public Optional<Genre> findGenreByName(String name) throws HibernateException {
        String query = "SELECT g FROM Genre g WHERE g.name = :name";
        return executeQuery(query, "name", name, Genre.class);
    }
}
