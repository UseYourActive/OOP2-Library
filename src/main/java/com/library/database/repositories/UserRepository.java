package com.library.database.repositories;

import com.library.database.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.stream.Stream;

public class UserRepository extends Repository<User> {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    public UserRepository() {
        super();
    }

    @Override
    public User get(Long id) {
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
