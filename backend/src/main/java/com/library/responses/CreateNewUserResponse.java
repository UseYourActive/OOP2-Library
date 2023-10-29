package com.library.responses;

import com.library.annotations.PhoneNumber;
import com.library.annotations.StrongPassword;
import com.library.operations.base.OperationOutput;
import jakarta.validation.constraints.Email;
import lombok.*;

//@Getter
//@Setter(AccessLevel.PRIVATE)
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
