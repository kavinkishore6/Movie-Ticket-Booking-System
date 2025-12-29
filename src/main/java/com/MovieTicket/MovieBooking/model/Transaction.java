package com.MovieTicket.MovieBooking.model;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {

    @NotBlank(message = "Order ID is required")
    private String orderId;

    @NotBlank(message = "Currency is required")
    private String currency;

    @Positive(message = "Amount must be positive")
    private int amount;

    @NotBlank(message = "Key is required")
    private String key;

    public Transaction(String orderId, String currency, int amount, String key) {
        this.orderId = orderId;
        this.currency = currency;
        this.amount = amount;
        this.key = key;
    }
}
