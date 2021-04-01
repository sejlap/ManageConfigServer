package com.example.rentservice.rentservice.Repositories;
import com.example.rentservice.rentservice.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}