package com.library.repositories;

import com.library.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {

}
