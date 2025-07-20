package com.example.MovieBookingApplication.services;

import com.example.MovieBookingApplication.dto.ShowDTO;
import com.example.MovieBookingApplication.entities.Booking;
import com.example.MovieBookingApplication.entities.Movie;
import com.example.MovieBookingApplication.entities.Show;
import com.example.MovieBookingApplication.entities.Theater;
import com.example.MovieBookingApplication.repositories.MoviesRepository;
import com.example.MovieBookingApplication.repositories.ShowRepository;
import com.example.MovieBookingApplication.repositories.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MoviesRepository moviesRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    public Show createShow(ShowDTO showDTO) {
        Movie movie = moviesRepository.findById(showDTO.getMovieId())
                .orElseThrow(() -> new RuntimeException("No movie found with title: " + showDTO.getMovieId()));

        Theater theater = theaterRepository.findById(showDTO.getTheaterId())
                .orElseThrow(() -> new RuntimeException("No theater found with id: " + showDTO.getTheaterId()));

        Show show = new Show();
        show.setShowTime(null);
        show.setPrice(null);
        show.setMovie(movie);
        show.setTheater(theater);

        return showRepository.save(show);
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public List<Show> getShowsByMovie(Long movieid) {
        Optional<List<Show>> showListBox = showRepository.findByMovieId(movieid);
        if (showListBox.isPresent()) {
            return showListBox.get();
        } else {
            throw new RuntimeException("No shows found for movie with id: " + movieid);
        }
    }

    public List<Show> getShowsByTheater(Long theaterid) {
        Optional<List<Show>> showListBox = showRepository.findByTheaterId(theaterid);
        if (showListBox.isPresent()) {
            return showListBox.get();
        } else {
            throw new RuntimeException("No shows found for Theater with id: " + theaterid);
        }
    }

    public Show updateShow(Long id, ShowDTO showDTO) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No show found with id: " + id));

        Movie movie = moviesRepository.findById(showDTO.getMovieId())
                .orElseThrow(() -> new RuntimeException("No movie found with title: " + showDTO.getMovieId()));

        Theater theater = theaterRepository.findById(showDTO.getTheaterId())
                .orElseThrow(() -> new RuntimeException("No theater found with id: " + showDTO.getTheaterId()));

        show.setShowTime(null);
        show.setPrice(null);
        show.setMovie(movie);
        show.setTheater(theater);

        return showRepository.save(show);
    }

    public void deleteShow(Long id) {
        if (!showRepository.existsById(id)) {
            throw new RuntimeException("No show found with id: " + id);
        }

        List<Booking> bookings = showRepository.findById(id).get().getBookings();
        if(!bookings.isEmpty()){
            throw new RuntimeException("Cannot delete show with existing bookings");
        }
        showRepository.deleteById(id);
    }




}
