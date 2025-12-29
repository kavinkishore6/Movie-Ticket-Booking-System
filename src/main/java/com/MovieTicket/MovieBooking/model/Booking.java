package com.MovieTicket.MovieBooking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "User is required")
    private User user;

    @ManyToOne
    @NotNull(message = "Show is required")
    private Show show;

    @NotBlank(message = "Seat numbers cannot be empty")
    @Size(max = 100, message = "Seat numbers too long")
    private String seatNumbers;

    @Positive(message = "Total amount must be positive")
    private double totalAmount;

    @Column(nullable = false)
    private LocalDateTime bookingTime;

    @PrePersist
    protected void onCreate() {
        bookingTime = LocalDateTime.now();
    }
}
