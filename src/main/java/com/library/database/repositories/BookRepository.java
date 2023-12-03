package com.library.database.repositories;

import com.library.database.entities.Book;

import java.util.UUID;
import java.util.stream.Stream;

public class BookRepository extends Repository<Book> {

    public BookRepository() {
        super();
    }

    @Override
    public Book get(UUID id) {
        return null;
    }

    @Override
    public Stream<Book> getAll() {
        return null;
    }

    public Book getByTitle(String title) {
        try {
            return session.createQuery("SELECT b FROM BOOK b", Book.class).getResultStream()
                    .filter(b -> b.getTitle().equals(title))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("User not found"));
        } catch (Exception e) {
            return null;
        }
    }
}
