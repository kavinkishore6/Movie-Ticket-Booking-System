package com.MovieTicket.MovieBooking.service;

import com.MovieTicket.MovieBooking.exception.GlobalExceptionHandler;
import com.MovieTicket.MovieBooking.model.Show;
import com.MovieTicket.MovieBooking.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private BookingService bookingService;

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public void saveShow(Show show) {
        showRepository.save(show);
    }

    public Show getShowById(Long id) {
        return showRepository.findById(id)
                .orElseThrow(() ->
                        new GlobalExceptionHandler.ShowNotFoundException(
                                "Show not found with id: " + id
                        )
                );
    }

    public List<Show> getShowsByMovieId(Long movieId) {
        return showRepository.findByMovieId(movieId);
    }

    public long getTotalShowsCount() {
        return showRepository.count();
    }

    @Transactional
    public List<Show> getTop10MoviesByPrice() {
        return showRepository.findTop10ByOrderByPriceDesc();
    }

    public List<Show> getShowsByGenre(String genre) {
        return showRepository.findShowsByMovieGenre(genre);
    }

    @Transactional
    public void deleteShow(Long id) {
        if (!showRepository.existsById(id)) {
            throw new GlobalExceptionHandler.ShowNotFoundException(
                    "Show not found with id: " + id
            );
        }
        bookingService.deleteByShowId(id);
        showRepository.deleteById(id);
    }
}
