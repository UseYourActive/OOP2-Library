package com.library.requests;

import com.library.annotations.PhoneNumber;
import com.library.annotations.StrongPassword;
import com.library.operations.base.OperationInput;
import jakarta.validation.constraints.Email;
import lombok.*;

//@Getter
//@Setter(AccessLevel.PRIVATE)
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
