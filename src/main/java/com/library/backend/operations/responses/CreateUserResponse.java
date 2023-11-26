package com.library.backend.operations.responses;


import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class CreateUserResponse implements OperationOutput {
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private String role;
    private String email;
    private String phone;
}
