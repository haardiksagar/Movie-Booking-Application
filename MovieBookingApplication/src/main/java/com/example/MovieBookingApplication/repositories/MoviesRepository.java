package com.example.MovieBookingApplication.repositories;

import com.example.MovieBookingApplication.entities.Movie;
import com.example.MovieBookingApplication.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MoviesRepository extends JpaRepository<Movie, Long> {
    // This interface will automatically provide CRUD operations for the Movie entity
    // Additional custom query methods can be defined here if needed
    Optional<List<Movie>> findByGenre(String genre);
    Optional<List<Movie>> findByLanguage(String language);
    Optional <Movie> findByName(String title);
}
