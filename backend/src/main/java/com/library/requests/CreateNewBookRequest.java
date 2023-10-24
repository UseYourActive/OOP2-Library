package com.library.requests;

import com.library.operations.base.OperationInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateNewBookRequest implements OperationInput {
    private String title;
    private String resume;
//    private List<Author> author;
    private String isbn;
//    private Genre genre;
}
