package com.library.libraryproject.database.repositories;

import com.library.libraryproject.database.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
}
