package com.library.libraryproject.database.repositories;

import com.library.libraryproject.database.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
