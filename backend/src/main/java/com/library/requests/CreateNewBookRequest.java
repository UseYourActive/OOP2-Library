package com.library.requests;

import com.library.operations.base.OperationInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

//@Getter
@Builder
@AllArgsConstructor
public class CreateNewBookRequest implements OperationInput {
    private String title;
    private String resume;
//    private List<Author> author;
    private String isbn;
//    private Genre genre;


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
