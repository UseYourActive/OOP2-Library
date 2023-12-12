package com.library.backend.operations.responses;


import com.library.database.enums.Role;
import lombok.*;


@Getter
@Builder
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class LogInResponse implements Response {
    private String userId;
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private Role role;
    private String email;
}
