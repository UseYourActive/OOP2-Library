package com.library.database.entities.base;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@MappedSuperclass
public class Client extends User {
    @Column(name = "first_name", length = 16, nullable = false)
    protected String firstName;

    @Column(name = "middle_name", length = 16, nullable = false)
    protected String middleName;

    @Column(name = "last_name", length = 16, nullable = false)
    protected String lastName;

    @Setter
    @Column(name = "email", length = 32, unique = true, nullable = false)
    protected String email;
}
