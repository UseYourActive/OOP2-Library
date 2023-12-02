package com.library.database.repositories;

import com.library.database.entities.Author;

import java.util.UUID;
import java.util.stream.Stream;

public class AuthorRepository extends Repository<Author> {
    @Override
    public Author get(UUID id) {
        return session.get(Author.class,id);
    }

    @Override
    public Stream<Author> getAll() {
        return session.createQuery("SELECT a FROM AUTHORS a",Author.class).getResultStream();
    }

    public Author getByName(String author){
        try{
            return session.createQuery("SELECT a FROM AUTHORS a",Author.class).getResultStream()
                    .filter(a -> a.getName().equals(author))
                    .findFirst()
                    .orElseThrow(()-> new RuntimeException("Author not found"));
        }catch (Exception e){
            return null;
        }
    }

}
