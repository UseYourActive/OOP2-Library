package com.library.responses;

import com.library.annotations.PhoneNumber;
import com.library.annotations.StrongPassword;
import com.library.operations.base.OperationOutput;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class CreateNewUserResponse implements OperationOutput {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String role;

    @Email
    private String email;

    @PhoneNumber
    private String phone;

    @StrongPassword
    private String password;
}
