package com.example.MovieBookingApplication.controllers;

import com.example.MovieBookingApplication.dto.ShowDTO;
import com.example.MovieBookingApplication.entities.Show;
import com.example.MovieBookingApplication.services.ShowService;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/show")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping("/createshow") // API to create a new show
    public ResponseEntity<Show> createShow(@RequestBody ShowDTO showDTO) {
        // Logic to create a show
        return ResponseEntity.ok(showService.createShow(showDTO));
    }

    @GetMapping("/getallshows") // API to get all shows
    public ResponseEntity<List<Show>> getAllShows() {
        // Logic to get all shows
        return ResponseEntity.ok(showService.getAllShows());
    }

    @GetMapping("/getshowsbymovie/{id}") // API to get shows by theater
    public ResponseEntity<List<Show>> getShowsByMovie(@PathVariable Long id){

        return ResponseEntity.ok(showService.getShowsByMovie(id));
    }

    @GetMapping("/getshowsbytheater/{id}") // API to get shows by theater
    public ResponseEntity<List<Show>> getShowsByTheater(@PathVariable Long id){

        return ResponseEntity.ok(showService.getShowsByTheater(id));
    }

    @PutMapping("/updateshow/{id}") // API to update a show
    public ResponseEntity<Show> updateShow(@PathVariable Long id, @RequestBody ShowDTO showDTO) {
        // Logic to update a show
        return ResponseEntity.ok(showService.updateShow(id, showDTO));
    }

    @DeleteMapping("/deleteshow/{id}") // API to delete a show
    public ResponseEntity<Void> deleteShow(@PathVariable Long id) {
        // Logic to delete a show
        showService.deleteShow(id);
        return ResponseEntity.ok().build();
    }

}
