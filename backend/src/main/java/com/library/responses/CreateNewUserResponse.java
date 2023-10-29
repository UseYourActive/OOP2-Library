package com.library.responses;

import com.library.annotations.PhoneNumber;
import com.library.annotations.StrongPassword;
import com.library.operation.base.OperationOutput;
import jakarta.validation.constraints.Email;
import lombok.*;

//@Getter
//@Setter(AccessLevel.PRIVATE)
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Builder
//@AllArgsConstructor
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

    public CreateNewUserResponse(String id, String username, String firstName, String lastName, String role, String email, String phone, String password) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public static class CreateNewUserResponseBuilder {
        private String id;
        private String username;
        private String firstName;
        private String lastName;
        private String role;
        private String email;
        private String phone;
        private String password;

        public CreateNewUserResponseBuilder id(String id) {
            this.id = id;
            return this;
        }

        public CreateNewUserResponseBuilder username(String username) {
            this.username = username;
            return this;
        }

        public CreateNewUserResponseBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public CreateNewUserResponseBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public CreateNewUserResponseBuilder role(String role) {
            this.role = role;
            return this;
        }

        public CreateNewUserResponseBuilder email(String email) {
            this.email = email;
            return this;
        }

        public CreateNewUserResponseBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public CreateNewUserResponseBuilder password(String password) {
            this.password = password;
            return this;
        }

        public CreateNewUserResponse build(){
            return new CreateNewUserResponse(this.id, this.username, this.firstName, this.lastName, this.role, this.email, this.phone, this.password);
        }
    }

    public static CreateNewUserResponseBuilder builder(){
        return new CreateNewUserResponseBuilder();
    }

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
