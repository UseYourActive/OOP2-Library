package com.library.database.entities;

import com.library.database.entities.base.Client;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "operators")
@PrimaryKeyJoinColumn(name = "user_id")
public class Operator extends Client {
    @OneToMany(mappedBy = "operator", cascade = CascadeType.ALL)
    private List<Reader> listOfReaders;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;
}
