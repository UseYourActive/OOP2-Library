package com.library.database.repositories;

import com.library.database.entities.Author;
import jakarta.persistence.NoResultException;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class AuthorRepository extends Repository<Author> {
    private static final Logger logger = LoggerFactory.getLogger(AuthorRepository.class);
    private static volatile AuthorRepository instance;

    private AuthorRepository() {
    }

    public static AuthorRepository getInstance() {
        if (instance == null) {
            synchronized (AuthorRepository.class) {
                if (instance == null) {
                    instance = new AuthorRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public Optional<Author> findById(Long id) throws HibernateException {
        try {
            Author author = session.get(Author.class, id);
            if (author != null) {
                logger.info("Successfully found author with id: {}", id);
            } else {
                logger.info("No author found with id: {}", id);
                // maybe throw an exception?
            }
            return Optional.ofNullable(author);
        } catch (HibernateException e) {
            logger.error("Error finding author by ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public List<Author> findAll() throws HibernateException {
        try {
            return session.createQuery("SELECT a FROM Author a", Author.class).getResultList();
        } catch (HibernateException e) {
            logger.error("Error retrieving all authors", e);
            throw e;
        }
    }

    @Override
    public Author getById(Long id) {
        Author author = session.get(Author.class, id);
        if (author != null) {
            logger.info("Successfully found author with id: {}", id);
        }
        return author;
    }

    private <T> Optional<T> executeQuery(String query, String paramName, String paramValue, Class<T> resultType) throws HibernateException {
        try {
            return Optional.ofNullable(
                    session.createQuery(query, resultType)
                            .setParameter(paramName, paramValue)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            logger.info("No result found with {} : {}", paramName, paramValue);
            return Optional.empty();
        } catch (HibernateException e) {
            logger.error("Error executing query: {}", query, e);
            throw e;
        }
    }

    public Optional<Author> findByName(String authorName) throws HibernateException {
        String query = "SELECT a FROM Author a WHERE a.name = :name";
        return executeQuery(query, "name", authorName, Author.class);
    }

    public List<Author> findAllAuthorsByName(String authorName) throws HibernateException {
        String query = "SELECT a FROM Author a WHERE a.name = :name";
        return session.createQuery(query, Author.class)
                .setParameter("name", authorName)
                .getResultList();
    }

    public List<Author> findAllAuthorsByCountry(String country) throws HibernateException {
        String query = "SELECT a FROM Author a WHERE a.country = :country";
        return session.createQuery(query, Author.class)
                .setParameter("country", country)
                .getResultList();
    }
}