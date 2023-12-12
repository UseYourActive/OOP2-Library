package com.library.backend.services;

import com.library.database.entities.Book;
import com.library.database.entities.User;
import com.library.database.enums.BookStatus;
import com.library.database.repositories.BookRepository;
import com.library.database.repositories.UserRepository;

import java.util.List;

public class AdminService implements Service {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public AdminService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public void archiveBook(Book book){
        book.setBookStatus(BookStatus.ARCHIVED);
    }

    public boolean registerBook(Book book){
        return bookRepository.save(book);
    }

    public void removeOperator(User operator){
        userRepository.delete(operator);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }
}
