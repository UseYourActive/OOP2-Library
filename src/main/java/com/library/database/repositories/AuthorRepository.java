package com.library.database.repositories;

import com.library.database.entities.Author;
import jakarta.persistence.NoResultException;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * The {@code AuthorRepository} class provides specific repository operations for managing {@link Author} entities
 * using Hibernate as the underlying ORM (Object-Relational Mapping) framework. It extends the generic {@link Repository}
 * class and implements methods for finding authors by various criteria.
 *
 * <p>This class follows the Singleton design pattern to ensure a single instance is used across the application,
 * promoting consistency in entity management.</p>
 *
 * <p>Usage of this class includes finding authors by ID, retrieving all authors, getting authors by ID, finding authors
 * by name, finding all authors with a specific name, and finding all authors from a particular country.</p>
 *
 * <p>Note: This class is thread-safe due to the Singleton pattern, making it suitable for use in multi-threaded
 * environments.</p>
 *
 * @see Author
 * @see Repository
 * @see HibernateException
 * @see Logger
 */
public class AuthorRepository extends Repository<Author> {
    private static final Logger logger = LoggerFactory.getLogger(AuthorRepository.class);
    private static volatile AuthorRepository instance;

    private AuthorRepository() {
    }

    /**
     * Gets the singleton instance of the {@code AuthorRepository}.
     *
     * @return The singleton instance of the {@code AuthorRepository}.
     */
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

    /**
     * Find and return an author by their unique identifier (ID). This method utilizes the Hibernate session to perform
     * the database operation. If the author is not found, an empty {@code Optional} is returned.
     *
     * @param id The ID of the author to find.
     * @return An {@code Optional} containing the found author, or empty if the author is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
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

    /**
     * Retrieve and return a list of all authors. This method uses Hibernates HQL (Hibernate Query Language) to execute
     * a query to fetch all authors.
     *
     * @return A list containing all authors.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    @Override
    public List<Author> findAll() throws HibernateException {
        try {
            return session.createQuery("SELECT a FROM Author a", Author.class).getResultList();
        } catch (HibernateException e) {
            logger.error("Error retrieving all authors", e);
            throw e;
        }
    }

    /**
     * Get and return an author by their unique identifier (ID). This method is similar to {@link #findById(Long)}, but
     * it returns the author directly instead of wrapping it in an {@code Optional}. If the author is not found, it returns null.
     *
     * @param id The ID of the author to get.
     * @return The found author, or null if the author is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    @Override
    public Author getById(Long id) {
        Author author = session.get(Author.class, id);
        if (author != null) {
            logger.info("Successfully found author with id: {}", id);
        }
        return author;
    }

    /**
     * Execute a parameterized query and return the result as an {@code Optional}. If no result is found, an empty
     * {@code Optional} is returned. This method is used for common logic in methods that find authors by specific criteria.
     *
     * @param query      The HQL query to execute.
     * @param paramName  The name of the parameter in the query.
     * @param paramValue The value of the parameter.
     * @param resultType The type of the result expected.
     * @param <T>        The type of the result.
     * @return An {@code Optional} containing the result of the query, or empty if no result is found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
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

    /**
     * Find and return an author by their name. This method uses a parameterized query to search for an author with the
     * specified name. If the author is not found, an empty {@code Optional} is returned.
     *
     * @param authorName The name of the author to find.
     * @return An {@code Optional} containing the found author, or empty if the author is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    public Optional<Author> findByName(String authorName) throws HibernateException {
        String query = "SELECT a FROM Author a WHERE a.name = :name";
        return executeQuery(query, "name", authorName, Author.class);
    }

    /**
     * Find and return a list of authors with the specified name. This method uses a parameterized query to search for
     * authors with the given name.
     *
     * @param authorName The name of the authors to find.
     * @return A list containing all authors with the specified name.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    public List<Author> findAllAuthorsByName(String authorName) throws HibernateException {
        String query = "SELECT a FROM Author a WHERE a.name = :name";
        return session.createQuery(query, Author.class)
                .setParameter("name", authorName)
                .getResultList();
    }

    /**
     * Find and return a list of authors from the specified country. This method uses a parameterized query to search for
     * authors with the given country.
     *
     * @param country The country of the authors to find.
     * @return A list containing all authors from the specified country.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    public List<Author> findAllAuthorsByCountry(String country) throws HibernateException {
        String query = "SELECT a FROM Author a WHERE a.country = :country";
        return session.createQuery(query, Author.class)
                .setParameter("country", country)
                .getResultList();
    }
}