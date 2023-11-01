package com.library.requests;

import com.library.annotations.Date;
import com.library.operations.base.OperationInput;
import lombok.*;

import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class CreateNewAuthorRequest implements OperationInput {
    private String firstName;
    private String secondName;
    private String lastName;
    @Date
    private String dateOfBirth;
    private String country;
    private List<String> bookIds;
}
