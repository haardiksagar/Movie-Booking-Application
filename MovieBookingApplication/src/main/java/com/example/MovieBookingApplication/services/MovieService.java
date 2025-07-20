package com.example.MovieBookingApplication.services;

import com.example.MovieBookingApplication.dto.MovieDTO;
import com.example.MovieBookingApplication.entities.Movie;
import com.example.MovieBookingApplication.repositories.MoviesRepository;
import org.hibernate.ConnectionReleaseMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MoviesRepository moviesRepository;

    public Movie addMovie(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setName(movieDTO.getName());
        movie.setDescription(movieDTO.getDescription());
        movie.setGenre(movieDTO.getGenre());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setDuration(movieDTO.getDuration());
        movie.setLanguage(movieDTO.getLanguage());

        return moviesRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return moviesRepository.findAll();
    }

    public List<Movie> getMoviesByGenre(String genre) {
        Optional<List<Movie>> ListOfMovieBox = moviesRepository.findByGenre(genre);

        if (ListOfMovieBox.isPresent()) {
            return ListOfMovieBox.get();
        } else {
            throw new RuntimeException("No movies found for the specified genre: " + genre);
        }
    }
    public List<Movie> getMoviesByLanguage(String language) {
        Optional<List<Movie>> ListOfMovieBox = moviesRepository.findByLanguage(language);

        if (ListOfMovieBox.isPresent()) {
            return ListOfMovieBox.get();
        } else {
            throw new RuntimeException("No movies found for the specified language: " + language);
        }
    }

    public Movie getMoviesByTitle(String title) {
        Optional<Movie> movieBox = moviesRepository.findByName(title);

        if (movieBox.isPresent()) {
            return movieBox.get();
        } else {
            throw new RuntimeException("No movie found with the specified title: " + title);
        }
    }

    public Movie updateMovie(Long id, MovieDTO movieDTO) {
        Movie movie = moviesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));

        // Update the movie details with the values from the DTO
            movie.setName(movieDTO.getName());
            movie.setDescription(movieDTO.getDescription());
            movie.setGenre(movieDTO.getGenre());
            movie.setReleaseDate(movieDTO.getReleaseDate());
            movie.setDuration(movieDTO.getDuration());
            movie.setLanguage(movieDTO.getLanguage());

            return moviesRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        moviesRepository.deleteById(id);
    }



}
