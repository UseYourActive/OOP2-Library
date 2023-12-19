package com.library.backend.services;

import com.library.database.entities.Author;
import com.library.database.entities.Book;
import com.library.database.entities.BookForm;
import com.library.database.entities.User;
import com.library.database.enums.BookStatus;
import com.library.database.repositories.AuthorRepository;
import com.library.database.repositories.BookFormRepository;
import com.library.database.repositories.BookRepository;
import com.library.database.repositories.UserRepository;

import java.util.List;

public class AdminService implements Service {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    private final AuthorRepository authorRepository;

    private final BookFormRepository bookFormRepository;

    public AdminService(BookRepository bookRepository, UserRepository userRepository,AuthorRepository authorRepository,BookFormRepository bookFormRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.authorRepository=authorRepository;
        this.bookFormRepository=bookFormRepository;
    }

    public boolean archiveBook(Book book){
        book.setBookStatus(BookStatus.ARCHIVED);
        return bookRepository.save(book);
    }

    public boolean registerBook(Book book){
        return bookRepository.save(book);
    }

    public void removeBook(Book book){ bookRepository.delete(book); }

    public void removeOperator(User operator){
        userRepository.delete(operator);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public List<Book> getBooks(){
        return bookRepository.findAll();
    }
}
