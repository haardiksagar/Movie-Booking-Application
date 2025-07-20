package com.example.MovieBookingApplication.repositories;

import com.example.MovieBookingApplication.entities.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShowRepository extends JpaRepository<Show, Long> {
    // This interface will automatically have methods for CRUD operations
    // like save, findById, findAll, deleteById, etc.
    Optional<List<Show>> findByMovieId(Long movieId);
    Optional<List<Show>> findByTheaterId(Long theaterId);

}
