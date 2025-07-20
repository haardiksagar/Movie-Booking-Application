package com.example.MovieBookingApplication.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated primary key
    private Long id;
    private String name;
    private String description;
    private String genre;
    private Integer duration; // in minutes
    private LocalDate releaseDate;
    private String language;
    //now One entity can map to some other entity as well. Here this movie Entity can map multiple shows Enitity
    // as one movie can have multiple shows in a day.
    //So we require a "One To Many" relationship so use a "List<Show> shows;"
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)// Establishing a one-to-many relationship with Show entity
    private List<Show> show;
/*## Me:
meaning this will not load the data related to the specific shows in the list instead we will only see the list of shows and to access the data of a specific show, it needs to be done explicitly

## AI Assistant:
Yes, your understanding is correct. When you use `fetch = FetchType.LAZY`:

- The **list of shows** (`List<Show>`) related to the specific `Movie` will not be loaded immediately when you fetch the `Movie` entity from the database.
- Instead, a **proxy object** (or a placeholder for the `List<Show>`) will be created. This proxy will act as a reference to the related data but will not load the actual records.
- To access the actual data of the related `Show` entities, it will need to be fetched explicitly by accessing the list or iterating through it in your code. At that point, the database query to fetch the related `Show` entities will be triggered.

This mechanism helps optimize performance by avoiding unnecessary database queries when you don’t explicitly need the related data.
*/ //`fetch = FetchType.LAZY)` what does this mean


    // Additional fields and methods can be added as needed
}
