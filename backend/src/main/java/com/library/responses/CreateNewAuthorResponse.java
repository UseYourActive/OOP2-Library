package com.library.responses;

import com.library.operations.base.OperationOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class CreateNewAuthorResponse implements OperationOutput {
    private String firstName;
    private String secondName;
    private String lastName;
    private String dateOfBirth;
    private String country;
    private List<String> books;
}
