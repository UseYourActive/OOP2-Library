package com.library.database.repositories;

import com.library.database.entities.Book;
import jakarta.persistence.NoResultException;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class BookRepository extends Repository<Book> {
    private static final Logger logger = LoggerFactory.getLogger(BookRepository.class);
    private static volatile BookRepository instance;

    private BookRepository() {
    }

    public static BookRepository getInstance() {
        if (instance == null) {
            synchronized (BookRepository.class) {
                if (instance == null) {
                    instance = new BookRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public Optional<Book> findById(Long id) throws HibernateException {
        try {
            Book book = session.get(Book.class, id);
            if (book != null) {
                logger.info("Successfully found book with id: {}", id);
            } else {
                logger.info("No book found with id: {}", id);
                // maybe throw an exception?
            }
            return Optional.ofNullable(book);
        } catch (HibernateException e) {
            logger.error("Error finding book by ID: {}", id, e);
            throw e;
        }
    }

    public void deleteAll(Collection<Book> entities) throws HibernateException {
        executeInsideTransaction(session -> {
            for (Book entity : entities) {
                session.remove(entity);
                logger.info("Entity with ID {} deleted successfully", entity.getId());
            }
        });
    }

    @Override
    public List<Book> findAll() throws HibernateException {
        try {
            return session.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        } catch (HibernateException e) {
            logger.error("Error retrieving all books", e);
            throw e;
        }
    }

    @Override
    public Book getById(Long id) {
        Book book = session.get(Book.class, id);
        if (book != null) {
            logger.info("Successfully found book with id: {}", id);
        }
        return book;
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

    public Optional<Book> findByIsbn(String isbn) throws HibernateException {
        String query = "SELECT b FROM Book b WHERE b.isbn = :isbn";
        return executeQuery(query, "isbn", isbn, Book.class);
    }

    public Optional<Book> findByTitle(String title) throws HibernateException {
        String query = "SELECT b FROM Book b WHERE b.title = :title";
        return executeQuery(query, "title", title, Book.class);
    }

    public Optional<Book> findByInventoryNumber(String inventoryNumber) throws HibernateException {
        String query = "SELECT b FROM Book b WHERE b.inventoryNumber = :inventoryNumber";
        return executeQuery(query, "inventoryNumber", inventoryNumber, Book.class);
    }
}
