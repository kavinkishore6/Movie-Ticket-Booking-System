package com.MovieTicket.MovieBooking.service;

import com.MovieTicket.MovieBooking.exception.GlobalExceptionHandler;
import com.MovieTicket.MovieBooking.model.Transaction;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class orderServices {

    private static final String KEY = "rzp_test_bsbZpZTphceAM9";
    private static final String KEY_SECRET = "zNH3uitbomyOness2n1WBmwm";
    private static final String CURRENCY = "INR";

    public Transaction orderCreateTransaction(double amount) {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount", amount * 100);
            jsonObject.put("currency", CURRENCY);

            RazorpayClient razorpayClient =
                    new RazorpayClient(KEY, KEY_SECRET);

            Order order = razorpayClient.orders.create(jsonObject);

            return orderTransaction(order);

        } catch (Exception e) {
            throw new GlobalExceptionHandler.PaymentException(
                    "Failed to create Razorpay order: " + e.getMessage()
            );
        }
    }

    private Transaction orderTransaction(Order order) {

        String orderId = order.get("id");
        String currency = order.get("currency");
        int amount = order.get("amount");

        return new Transaction(orderId, currency, amount, KEY);
    }
}
