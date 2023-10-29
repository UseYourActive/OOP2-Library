package com.library.responses;

import com.library.operation.base.OperationOutput;

import java.util.UUID;

public class CreateNewBookResponse implements OperationOutput {
    private UUID BookId;
    private String title;
    private String resume;
    //    private List<Author> author;
    private String isbn;
//    private Genre genre;


    public UUID getBookId() {
        return BookId;
    }

    public void setBookId(UUID bookId) {
        BookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
