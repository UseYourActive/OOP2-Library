package com.library.responses;

import com.library.operations.base.OperationOutput;

import java.util.UUID;

public class CreateNewBookResponse implements OperationOutput {
    private UUID BookId;
    private String title;
    private String resume;
    //    private List<Author> author;
    private String isbn;
//    private Genre genre;
}
