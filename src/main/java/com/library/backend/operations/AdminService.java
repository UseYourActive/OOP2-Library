package com.library.backend.operations;

import com.library.database.entities.Book;
import com.library.database.entities.User;
import com.library.database.enums.BookStatus;
import com.library.database.repositories.BookRepository;
import com.library.database.repositories.UserRepository;

public class AdminService extends Service {
    private BookRepository bookRepository;
    private UserRepository userRepository;

    public void archiveBook(Book book){
        book.setBookStatus(BookStatus.ARCHIVED);
    }

    public boolean registerBook(Book book){
        return bookRepository.save(book);
    }

    public void removeOperator(User operator){
        userRepository.delete(operator);
    }

}
