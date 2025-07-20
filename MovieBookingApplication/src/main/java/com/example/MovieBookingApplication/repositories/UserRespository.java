package com.example.MovieBookingApplication.repositories;

import com.example.MovieBookingApplication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRespository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
