package com.MovieTicket.MovieBooking.service;

import com.MovieTicket.MovieBooking.exception.GlobalExceptionHandler;
import com.MovieTicket.MovieBooking.model.Movie;
import com.MovieTicket.MovieBooking.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public void saveMovie(Movie movie) {
        movieRepository.save(movie);
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() ->
                        new GlobalExceptionHandler.MovieNotFoundException(
                                "Movie not found with id: " + id
                        )
                );
    }

    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new GlobalExceptionHandler.MovieNotFoundException(
                    "Movie not found with id: " + id
            );
        }
        movieRepository.deleteById(id);
    }

    public long getTotalMoviesCount() {
        return movieRepository.count();
    }

    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    public List<Movie> getNowShowing() {
        return movieRepository.findNowShowing();
    }

    public List<Movie> getUpcomingMovies() {
        return movieRepository.findUpcomingMovies();
    }

    public List<Movie> getNewReleases() {
        return movieRepository.findLatestMovies();
    }

    public List<Movie> getTop10ByShowPrice() {
        return movieRepository.findTop10MoviesByHighestShowPrice();
    }
}
