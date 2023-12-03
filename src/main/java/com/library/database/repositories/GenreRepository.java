package com.library.database.repositories;

import com.library.database.entities.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class GenreRepository extends Repository<Genre> {
    private static final Logger logger = LoggerFactory.getLogger(GenreRepository.class);

    public GenreRepository() {
        super();
    }

    @Override
    public Genre get(Long id) {
        return session.get(Genre.class, id);
    }

    @Override
    public Stream<Genre> getAll() {
        return session.createQuery("SELECT g FROM Genre g", Genre.class).getResultStream();
    }

    public Genre getByName(String name) {
        return session.createQuery("SELECT g FROM Genre g", Genre.class).getResultStream()
                .filter(g -> g.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Genre not found!"));
    }
}
