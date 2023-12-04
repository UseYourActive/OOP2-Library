package com.library.database.entities;

import com.library.database.entities.base.Client;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "readers")
@SuperBuilder
@NoArgsConstructor
@Getter
public class Reader extends Client {
    //@OneToMany(mappedBy = "reader", orphanRemoval = true)
    private List<BookRequestForm> requestForms;
}
