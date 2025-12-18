package com.MovieTicket.MovieBooking.controller;

import com.MovieTicket.MovieBooking.model.*;
import com.MovieTicket.MovieBooking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class BookingController {
	
	@Autowired
    private BookingService bookingService;

        @GetMapping("/bookings/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return "redirect:/admin/bookings";
    }


    @GetMapping("/bookings")
    public String viewBookings(Model model) {
    	List<Booking> bookings = bookingService.getAllBookings();
        bookings.sort((b1, b2) -> b2.getBookingTime().compareTo(b1.getBookingTime()));
        model.addAttribute("bookings", bookings);
        return "admin/bookings";
    }


}
