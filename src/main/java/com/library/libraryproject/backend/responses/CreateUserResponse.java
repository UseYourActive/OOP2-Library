package com.library.libraryproject.backend.responses;

import com.library.libraryproject.backend.operations.base.OperationOutput;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateUserResponse implements OperationOutput {
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private String role;
    private String email;
    private String phone;
    private String password;
}
