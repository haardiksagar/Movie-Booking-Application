package com.example.MovieBookingApplication.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class MovieDTO {
    private Long id;
    private String name;
    private String description;
    private String genre;
    private Integer duration; // in minutes
    private LocalDate releaseDate;
    private String language;
}
