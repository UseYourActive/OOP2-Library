package com.project.database.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username", length = 32, unique = true, nullable = false)
    private String username;

    @Column(name = "first_name", length = 16, nullable = false)
    private String firstName;

    @Column(name = "middle_name", length = 16, nullable = false)
    private String middleName;

    @Column(name = "last_name", length = 16, nullable = false)
    private String lastName;

    @Column(name = "email", length = 32, unique = true, nullable = false)
    private String email;

    @Column(name = "password", length = 1024, nullable = false)
    private String password;


    //@ManyToOne
    //@JoinColumn(name = "role_id",nullable = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 64, nullable = false)
    private Role role;
}
