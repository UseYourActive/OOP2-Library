package com.library.database.repositories;

import com.library.database.entities.Book;
import jakarta.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class BookRepository extends Repository<Book> {
    private static final Logger logger = LoggerFactory.getLogger(BookRepository.class);
    public BookRepository() {
        super();
    }

    @Override
    public Book get(Long id) {
        return session.get(Book.class, id);
    }

    @Override
    public Stream<Book> getAll() {
        return session.createQuery("SELECT b FROM Book b", Book.class).getResultStream();
    }

    public Book getByTitle(String title) {
        try {
            return session.createQuery("SELECT b FROM Book b WHERE b.title = :title", Book.class)
                    .setParameter("title", title)
                    .getResultStream()
                    .findFirst()
                    .orElseThrow(() -> {
                        logger.error("Book not found with title: {}", title);
                        return new RuntimeException("Book not found with title: " + title);
                    });
        } catch (NoResultException e) {
            logger.error("Error querying book by title: {}", title, e);
            return null;
        }
    }
}
