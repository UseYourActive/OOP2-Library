package com.library.database.entities.base;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@MappedSuperclass
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "username", length = 32, unique = true, nullable = false)
    protected String username;

    @Column(name = "password", length = 1024, nullable = false)
    protected String password;
}