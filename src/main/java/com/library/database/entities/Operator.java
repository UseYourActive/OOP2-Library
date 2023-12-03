package com.library.database.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "operators")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Operator extends Client {
    private List<Reader> listOfReaders;
}
