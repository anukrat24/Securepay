package com.example.securepay.Repository;

import com.example.securepay.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);//find the user in the database by email
    //An Optional<User> — means the result may or may not exist (prevents null pointer exceptions).
    boolean existsByEmail(String email);//checks if the user exists in the database
}
