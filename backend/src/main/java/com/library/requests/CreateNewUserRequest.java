package com.library.requests;

import com.library.annotations.PhoneNumber;
import com.library.annotations.StrongPassword;
import com.library.operations.base.OperationInput;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class CreateNewUserRequest implements OperationInput {
    private String username;
    private String firstName;
    private String lastName;

    @Email
    private String email;

    @PhoneNumber
    private String phone;

    @StrongPassword
    private String password;
}
