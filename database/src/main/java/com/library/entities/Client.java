package com.library.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Table(name = "clients")
@Builder
public class Client extends User{
}
