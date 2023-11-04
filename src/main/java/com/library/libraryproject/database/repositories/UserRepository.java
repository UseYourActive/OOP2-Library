package com.library.libraryproject.database.repositories;

import com.library.libraryproject.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
