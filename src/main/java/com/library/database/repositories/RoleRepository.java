package com.library.database.repositories;

import com.library.database.entities.Role;

import java.util.UUID;
import java.util.stream.Stream;

public class RoleRepository extends Repository<Role> {
    public RoleRepository() {super();}

    @Override
    public Role get(UUID id) {
        return session.get(Role.class,id);
    }

    @Override
    public Stream<Role> getAll() {
        return session.createQuery("SELECT r FROM ROLES r",Role.class).getResultStream();
    }
}
