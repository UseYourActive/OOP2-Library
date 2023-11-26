package com.library.database.repositories;

import com.library.database.entities.Genre;
import com.library.database.repositories.contracts.Repository;

import java.util.UUID;
import java.util.stream.Stream;

public class GenreRepository extends Repository<Genre> {

    public GenreRepository() {super();}

    @Override
    public Genre get(UUID id) {
        return session.get(Genre.class,id);
    }

    @Override
    public Stream<Genre> getAll() {
        return session.createQuery("SELECT g FROM GENRES g", Genre.class).getResultStream();
    }

    public Genre getByName(String name){
        return session.createQuery("SELECT g FROM GENRES g",Genre.class).getResultStream()
                .filter(g->g.getName().equals(name))
                .findFirst()
                .orElseThrow(()->new RuntimeException("Genre not found!"));
    }
}
