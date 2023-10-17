package com.library.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Table(name = "operators")
@Builder
public class Operator extends User{
}
