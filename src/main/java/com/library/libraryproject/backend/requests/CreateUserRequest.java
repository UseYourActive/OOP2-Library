package com.library.libraryproject.backend.requests;

import com.library.libraryproject.backend.annotations.PhoneNumber;
import com.library.libraryproject.backend.annotations.StrongPassword;
import com.library.libraryproject.backend.operations.base.OperationInput;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateUserRequest implements OperationInput {
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;

    @Email
    private String email;

    @PhoneNumber
    private String phoneNumber;

    @StrongPassword
    private String password;
}
