package com.MovieTicket.MovieBooking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description too long")
    private String description;

    @NotBlank(message = "Genre is required")
    private String genre;

    @NotBlank(message = "Language is required")
    private String language;

    @NotNull(message = "Release date is required")
    private LocalDate releaseDate;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] posterData;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] backgroundData;

    @Positive(message = "Duration must be positive")
    private int duration;
}
