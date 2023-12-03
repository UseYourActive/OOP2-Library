package com.library.database.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class Client extends User {
    @Column(name = "first_name", length = 16, nullable = false)
    private String firstName;

    @Column(name = "middle_name", length = 16, nullable = false)
    private String middleName;

    @Column(name = "last_name", length = 16, nullable = false)
    private String lastName;

    @Column(name = "email", length = 32, unique = true, nullable = false)
    private String email;
}
