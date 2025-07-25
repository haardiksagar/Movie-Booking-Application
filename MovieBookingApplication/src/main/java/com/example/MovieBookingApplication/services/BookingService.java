package com.example.MovieBookingApplication.services;

import com.example.MovieBookingApplication.dto.BookingDTO;
import com.example.MovieBookingApplication.entities.Booking;
import com.example.MovieBookingApplication.entities.BookingStatus;
import com.example.MovieBookingApplication.entities.Show;
import com.example.MovieBookingApplication.entities.User;
import com.example.MovieBookingApplication.repositories.BookingRepository;
import com.example.MovieBookingApplication.repositories.ShowRepository;
import com.example.MovieBookingApplication.repositories.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private UserRespository userRepository;

    public Booking createBooking(BookingDTO bookingDTO) {
        Show show = showRepository.findById(bookingDTO.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found"));

        if(!isSeatsAvailable(show.getId(), bookingDTO.getNumberOfSeats())) {
            throw new RuntimeException("Not enough seats available");
        }

        if(bookingDTO.getSeatNumbers().size() != bookingDTO.getNumberOfSeats()) {
            throw new RuntimeException("Seat numbers and number of seats must be equal");
        }

        validateDuplicateSeats(show.getId(), bookingDTO.getSeatNumbers());

        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setNumberOfSeats(bookingDTO.getNumberOfSeats());
        booking.setSeatNumbers(bookingDTO.getSeatNumbers());
        booking.setPrice(calculateTotalPrice(show.getPrice(), bookingDTO.getNumberOfSeats()));
        booking.setBookingTime(LocalDateTime.now());
        booking.setBookingStatus(BookingStatus.PENDING);

        return bookingRepository.save(booking);
    }

    public List<Booking>getUserBookings(Long userid ){
        return bookingRepository.findByUserId(userid);
    }

    public List<Booking> getShowBookings(Long showid) {
        return bookingRepository.findByShowId(showid);
    }

    public Booking confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getBookingStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Booking is not in PENDING state");
        }

        //PAYMENT PROCESSING LOGIC HERE
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        return bookingRepository.save(booking);
    }

    public Booking cancelBooking(Long bookingId){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        validateCancellation(booking);

        booking.setBookingStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    public void validateCancellation(Booking booking) {
        LocalDateTime showTime = booking.getShow().getShowTime();
        LocalDateTime deadLineTime = showTime.minusHours(2); // Assuming cancellation is allowed 2 hours before the show time

        if( LocalDateTime.now().isAfter(deadLineTime)) {
            throw new RuntimeException("Cancellation is not allowed within 2 hours of the show time");
        }

        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking already been cancelled");
        }
    }

    public boolean isSeatsAvailable(Long showid , Integer numberOfSeats) {
        Show show = showRepository.findById(showid)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        int bookedseats = show.getBookings().stream()
                .filter(booking -> booking.getBookingStatus() != BookingStatus.CANCELLED)
                .mapToInt(Booking::getNumberOfSeats)
                .sum();
        return (show.getTheater().getTheaterCapacity() - bookedseats) >= numberOfSeats;
    }

    public void validateDuplicateSeats(Long showId, List<String> seatNumbers) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        Set<String> occupiedSeats = show.getBookings().stream()
                .filter(b -> b.getBookingStatus() != BookingStatus.CANCELLED)
                .flatMap(b -> b.getSeatNumbers().stream())
                .collect(Collectors.toSet());

        List<String> duplicateSeats = seatNumbers.stream()
                .filter(occupiedSeats::contains)
                .collect(Collectors.toList());


        if (!duplicateSeats.isEmpty()) {
            throw new RuntimeException("Seats are already booked");
        }
    }

    public Double calculateTotalPrice(Double price, Integer numberOfSeats) {
        return price * numberOfSeats;
    }

    public List<Booking> getBookingByStatus(BookingStatus bookingStatus) {
        return bookingRepository.findBookingByBookingStatus(bookingStatus);
    }


}
