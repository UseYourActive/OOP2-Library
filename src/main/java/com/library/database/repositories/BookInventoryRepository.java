package com.library.database.repositories;

import com.library.database.entities.BookInventory;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class BookInventoryRepository extends Repository<BookInventory> {
    private static final Logger logger = LoggerFactory.getLogger(BookInventoryRepository.class);
    private static volatile BookInventoryRepository instance;

    private BookInventoryRepository() {
    }

    public static BookInventoryRepository getInstance() {
        if (instance == null) {
            synchronized (BookInventoryRepository.class) {
                if (instance == null) {
                    instance = new BookInventoryRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public Optional<BookInventory> findById(Long id) throws HibernateException {
        try {
            BookInventory bookInventory = session.get(BookInventory.class, id);
            if (bookInventory != null) {
                logger.info("Successfully found BookInventory with id: {}", id);
            } else {
                logger.info("No BookInventory found with id: {}", id);
                // maybe throw an exception?
            }
            return Optional.ofNullable(bookInventory);
        } catch (HibernateException e) {
            logger.error("Error finding BookInventory by ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public List<BookInventory> findAll() throws HibernateException {
        try {
            return session.createQuery("SELECT b FROM BookInventory b", BookInventory.class).getResultList();
        } catch (HibernateException e) {
            logger.error("Error retrieving all books", e);
            throw e;
        }
    }

    @Override
    public BookInventory getById(Long id) throws HibernateException {
        BookInventory bookInventory = session.get(BookInventory.class, id);
        if (bookInventory != null) {
            logger.info("Successfully found book with id: {}", id);
        }
        return bookInventory;
    }
}
