package com.library.database.repositories;

import com.library.backend.exception.AdminNotFoundException;
import com.library.database.entities.Admin;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AdminRepository extends Repository<Admin>{

    private static final Logger logger = LoggerFactory.getLogger(AdminRepository.class);

    @Override
    public Optional<Admin> findById(Long id) {
        Admin admin = session.get(Admin.class, id);
        return Optional.ofNullable(admin);
    }

    @Override
    public List<Admin> findAll() {
        return session.createQuery("SELECT a FROM Admin a", Admin.class).getResultList();
    }

    public Admin findByUsername(String username) throws AdminNotFoundException {
        return session.createQuery("Select a from Admin a", Admin.class).stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new AdminNotFoundException("Admin not found"));
    }

}
