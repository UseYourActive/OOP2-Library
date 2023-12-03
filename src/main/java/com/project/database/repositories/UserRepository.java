package com.project.database.repositories;

import com.project.database.entities.User;

import java.util.UUID;
import java.util.stream.Stream;

public class UserRepository extends Repository<User> {
    public UserRepository() {
        super();
    }

    @Override
    public User get(UUID id) {
        return session.find(User.class, id);
    }

    @Override
    public Stream<User> getAll() {
        return session.createQuery("SELECT u FROM User u", User.class).getResultStream();
    }

    public User findByUsername(String username) throws Exception {
        return session.createQuery("Select u from User u", User.class).stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new Exception("User not found"));
    }
}
