package com.library.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Table(name = "admins")
@Builder
public class Admin extends User{
}
