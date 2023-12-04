package com.library.database.entities;

import com.library.database.entities.base.Client;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "operators")
@SuperBuilder
@NoArgsConstructor
@Getter
public class Operator extends Client {
    //@OneToMany(mappedBy = "",orphanRemoval = true)
    private List<Reader> listOfReaders;
}
