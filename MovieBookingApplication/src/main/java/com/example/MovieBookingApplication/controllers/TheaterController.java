package com.example.MovieBookingApplication.controllers;

import com.example.MovieBookingApplication.dto.TheaterDTO;
import com.example.MovieBookingApplication.entities.Theater;
import com.example.MovieBookingApplication.services.TheaterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theater")
public class TheaterController {

    private TheaterService theaterService;

    @PostMapping("/addtheater") // API to add a new theater
    @PreAuthorize("hasRole('ADMIN')") // Only users with ADMIN role can add a theater
    public ResponseEntity<Theater> addTheater(TheaterDTO theaterDTO) {
        return ResponseEntity.ok(theaterService.addTheater(theaterDTO));
    }

    @GetMapping("/gettheaterbylocation") // API to get theaters by location
    public ResponseEntity<List<Theater>> getTheaterByLocation(@RequestParam String location) {
        return ResponseEntity.ok(theaterService.getTheaterByLocation(location));
    }

    @PutMapping("/updatetheater/{id}") // API to update a theater
    @PreAuthorize("hasRole('ADMIN')") // Only users with ADMIN role can update a theater
    public ResponseEntity<Theater> updateTheater(@PathVariable Long id , @RequestBody TheaterDTO theaterDTO) {
        return ResponseEntity.ok(theaterService.updateTheater(id, theaterDTO));
    }

    @DeleteMapping("/deletetheater/{id}") // API to delete a theater
    @PreAuthorize("hasRole('ADMIN')") // Only users with ADMIN role can update a theater
    public ResponseEntity<Void> deleteTheater(@PathVariable Long id) {
        theaterService.deleteTheater(id);

        return ResponseEntity.ok().build();
    }

}
