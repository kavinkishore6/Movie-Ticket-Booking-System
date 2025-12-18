package com.MovieTicket.MovieBooking.repository;

import com.MovieTicket.MovieBooking.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

	boolean existsByEmail(String email);
}
	