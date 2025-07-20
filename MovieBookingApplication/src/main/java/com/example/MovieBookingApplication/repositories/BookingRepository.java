package com.example.MovieBookingApplication.repositories;

import com.example.MovieBookingApplication.entities.Booking;
import com.example.MovieBookingApplication.entities.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Custom query methods can be added here if needed
    // For example, to find bookings by user or show, etc.
    List<Booking> findByUserId(Long userId);
    List<Booking> findByShowId(Long showId);
    List<Booking> findBookingByBookingStatus(BookingStatus bookingStatus);
}
