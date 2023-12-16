package com.library.database.repositories;

import com.library.database.entities.Book;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class BookRepository extends Repository<Book> {
    private static final Logger logger = LoggerFactory.getLogger(BookRepository.class);

    @Override
    public Optional<Book> findById(Long id) {
        try {
            Book book = session.get(Book.class, id);
            return Optional.ofNullable(book);
        } catch (HibernateException e) {
            logger.error("Error finding book by ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public List<Book> findAll() {
        try {
            return session.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        } catch (HibernateException e) {
            logger.error("Error retrieving all books", e);
            throw e;
        }
    }

    @Override
    public Book getById(Long id) {
        logger.info("Successfully found book with id: {}", id);
        return session.get(Book.class, id);
    }

    public Optional<Book> findByIsbn(String isbn) {
        try {
            return Optional.ofNullable(
                    session.createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class)
                            .setParameter("isbn", isbn)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            logger.info("Book not found with ISBN: {}", isbn);
            return Optional.empty();
        } catch (HibernateException e) {
            logger.error("Error finding book by ISBN: {}", isbn, e);
            throw e;
        }
    }

    public Optional<Book> findByTitle(String title) {
        try {
            return Optional.ofNullable(
                    session.createQuery("SELECT b FROM Book b WHERE b.title = :title", Book.class)
                            .setParameter("title", title)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            logger.info("Book not found with title: {}", title);
            return Optional.empty();
        } catch (HibernateException e) {
            logger.error("Error finding book by title: {}", title, e);
            throw e;
        }
    }

    public Optional<Book> findByInventoryNumber(String inventoryNumber) {
        try {
            return Optional.ofNullable(
                    session.createQuery("SELECT b FROM Book b WHERE b.inventoryNumber = :inventoryNumber", Book.class)
                            .setParameter("inventoryNumber", inventoryNumber)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            logger.info("Book not found with inventory number: {}", inventoryNumber);
            return Optional.empty();
        } catch (HibernateException e) {
            logger.error("Error finding book by inventory number: {}", inventoryNumber, e);
            throw e;
        }
    }
}
