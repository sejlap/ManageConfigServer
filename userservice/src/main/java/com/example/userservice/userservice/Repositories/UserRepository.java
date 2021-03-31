package com.example.userservice.userservice.Repositories;
import com.example.userservice.userservice.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
        Optional<User> findByEmail(String email);
        Optional<User> deleteByEmail(String email);
}