package com.library.repositories;

import com.library.entities.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OperatorRepository extends JpaRepository<Operator, UUID> {
}
