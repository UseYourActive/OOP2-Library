package com.project.backend.operations.requests;


import com.project.backend.annotations.StrongPasswordValidation;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateUserRequest implements Request {
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;

    @Email
    private String email;

    @StrongPasswordValidation
    private String password;
}
