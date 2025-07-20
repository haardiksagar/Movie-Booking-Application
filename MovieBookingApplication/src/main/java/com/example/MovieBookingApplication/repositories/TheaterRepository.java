package com.example.MovieBookingApplication.repositories;

import com.example.MovieBookingApplication.entities.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TheaterRepository extends JpaRepository<Theater, Long> {
    // This interface will automatically provide CRUD operations for the Theater entity
    // Additional custom query methods can be defined here if needed

    Optional<List<Theater>> findTheaterBytheaterLocation(String location);

}
