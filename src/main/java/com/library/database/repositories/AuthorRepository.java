package com.library.database.repositories;

import com.library.database.entities.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class AuthorRepository extends Repository<Author> {
    private static final Logger logger = LoggerFactory.getLogger(AuthorRepository.class);

    @Override
    public Author get(Long id) {
        return session.get(Author.class, id);
    }

    @Override
    public Stream<Author> getAll() {
        return session.createQuery("SELECT a FROM Author a", Author.class).getResultStream();
    }

    public Author getByName(String author) {
        try {
            return session.createQuery("SELECT a FROM Author a", Author.class).getResultStream()
                    .filter(a -> a.getName().equals(author))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Author not found"));
        } catch (Exception e) {
            return null;
        }
    }

}
