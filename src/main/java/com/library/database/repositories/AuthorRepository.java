package com.library.database.repositories;

import com.library.database.entities.Author;
import jakarta.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class AuthorRepository extends Repository<Author> {
    private static final Logger logger = LoggerFactory.getLogger(AuthorRepository.class);

    @Override
    public Optional<Author> findById(Long id) {
        try {
            Author author = session.get(Author.class, id);
            return Optional.ofNullable(author);
        } catch (Exception e) {
            logger.error("Error finding author by ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public List<Author> findAll() {
        try {
            return session.createQuery("SELECT a FROM Author a", Author.class).getResultList();
        } catch (Exception e) {
            logger.error("Error retrieving all authors", e);
            throw e;
        }
    }

    public Optional<Author> findByName(String authorName) {
        try {
            return Optional.ofNullable(
                    session.createQuery("SELECT a FROM Author a WHERE a.name = :name", Author.class)
                            .setParameter("name", authorName)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            logger.info("Author not found with name: {}", authorName);
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Error finding author by name: {}", authorName, e);
            throw e;
        }
    }

    public List<Author> findAllAuthorsByName(String authorName) {
        try {
            return session.createQuery("SELECT a FROM Author a WHERE a.name = :name", Author.class)
                    .setParameter("name", authorName)
                    .getResultList();
        } catch (Exception e) {
            logger.error("Error finding authors by name: {}", authorName, e);
            throw e;
        }
    }

    public List<Author> findAllAuthorsByCountry(String country) {
        try {
            return session.createQuery("SELECT a FROM Author a WHERE a.country = :country", Author.class)
                    .setParameter("country", country)
                    .getResultList();
        } catch (Exception e) {
            logger.error("Error finding authors by country: {}", country, e);
            throw e;
        }
    }
}
