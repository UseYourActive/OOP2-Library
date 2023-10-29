package com.library.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    public User(UUID id, Role role, String username, String firstName, String lastName, String email, String phone, String password) {
        this.id = id;
        this.role = role;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public static UserBuilder builder(){
        return new UserBuilder();
    }

    public static class UserBuilder {
        private UUID id;
        private Role role;
        private String username;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String password;

        public UserBuilder() {
        }

        public UserBuilder id(final UUID id){
            this.id = id;
            return this;
        }

        public UserBuilder role(final Role role){
            this.role = role;
            return this;
        }

        public UserBuilder username(final String username){
            this.username = username;
            return this;
        }

        public UserBuilder firstName(final String firstName){
            this.firstName = firstName;
            return this;
        }

        public UserBuilder lastName(final String lastName){
            this.lastName = lastName;
            return this;
        }

        public UserBuilder email(final String email){
            this.email = email;
            return this;
        }

        public UserBuilder phone(final String phone){
            this.phone = phone;
            return this;
        }

        public UserBuilder password(final String password){
            this.password = password;
            return this;
        }

        public User build(){
            return new User(this.id, this.role, this.username, this.firstName, this.lastName, this.email, this.phone, this.password);
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
