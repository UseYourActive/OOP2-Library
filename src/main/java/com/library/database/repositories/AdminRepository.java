package com.library.database.repositories;

import com.library.database.entities.Admin;
import com.library.database.entities.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class AdminRepository extends Repository<Admin>{

    private static final Logger logger = LoggerFactory.getLogger(com.library.database.repositories.ReaderRepository.class);

    public AdminRepository() {
        super();
    }

    @Override
    public Admin get(Long id) {
        return session.find(Admin.class, id);
    }

    @Override
    public Stream<Admin> getAll() {
        return session.createQuery("SELECT a FROM Admin a", Admin.class).getResultStream();
    }

    public Admin findByUsername(String username) throws Exception {
        return session.createQuery("Select a from Admin a", Admin.class).stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new Exception("Admin not found"));
    }

}
