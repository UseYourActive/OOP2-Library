package com.library.database.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "genres")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Genre {

    @Id
    @Column(name = "genre_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name",nullable = false)
    private String name;
}
