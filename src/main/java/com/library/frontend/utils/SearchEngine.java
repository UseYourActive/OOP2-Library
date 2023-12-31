package com.library.frontend.utils;

import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.database.entities.Reader;
import com.library.database.entities.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchEngine {
    public List<BookInventory> searchBooks(List<BookInventory> inventories, String stringToSearch) {
        return inventories.stream()
                .filter(inventory -> containsSearchString(inventory.getBookList().get(0), stringToSearch))
                .collect(Collectors.toList());
    }

    public Set<User> searchOperators(List<User> userList, String stringToSearch) {
        return userList.stream()
                .filter(user -> user.getUsername().toUpperCase().contains(stringToSearch.toUpperCase()) ||
                        user.getRole().toString().toUpperCase().contains(stringToSearch.toUpperCase()))
                .collect(Collectors.toSet());
    }

    public Set<Reader> searchReaders(List<Reader> readerList, String stringToSearch) {
        return readerList.stream()
                .filter(reader -> reader.getFirstName().toUpperCase().contains(stringToSearch.toUpperCase()) ||
                        reader.getMiddleName().toUpperCase().contains(stringToSearch.toUpperCase()) ||
                        reader.getLastName().toUpperCase().contains(stringToSearch.toUpperCase()) ||
                        reader.getEmail().toUpperCase().contains(stringToSearch.toUpperCase()) ||
                        reader.getPhoneNumber().contains(stringToSearch.toUpperCase()))
                .collect(Collectors.toSet());
    }

    private boolean containsSearchString(Book book, String stringToSearch) {
        return book.getTitle().toUpperCase().contains(stringToSearch.toUpperCase()) ||
                book.getAuthor().toString().toUpperCase().contains(stringToSearch.toUpperCase()) ||
                book.getResume().toUpperCase().contains(stringToSearch.toUpperCase()) ||
                book.getGenre().toString().toUpperCase().contains(stringToSearch.toUpperCase()) ||
                (book.getPublishYear() != null && book.getPublishYear().toString().contains(stringToSearch));
    }
}
