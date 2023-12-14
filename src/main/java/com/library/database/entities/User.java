package com.library.database.entities;

import com.library.database.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "users")
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username", length = 32, unique = true, nullable = false)
    private String username;

    @Column(name = "password", length = 1024, nullable = false)
    private String password;

    @Column(name = "first_name", length = 16)
    private String firstName;

    @Column(name = "middle_name", length = 16)
    private String middleName;

    @Column(name = "last_name", length = 16)
    private String lastName;

    @Setter
    @Column(name = "email", length = 32, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 16,nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BookRequestForm> bookRequestForms;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
