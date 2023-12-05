package com.library.database.entities;

import com.library.database.entities.base.Client;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Getter
@SuperBuilder

@Entity
@Table(name = "readers")
@PrimaryKeyJoinColumn(name = "user_id")
public class Reader extends Client {
    @OneToMany(mappedBy = "reader", cascade = CascadeType.ALL)
    private List<BookRequestForm> requestForms;

    @ManyToOne
    @JoinColumn(name = "operator_id")
    private Operator operator;
}
