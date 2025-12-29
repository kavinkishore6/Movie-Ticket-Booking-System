package com.Test.mts.service;

import com.MovieTicket.MovieBooking.model.Movie;
import com.MovieTicket.MovieBooking.repository.MovieRepository;
import com.MovieTicket.MovieBooking.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    void getAllMovies_shouldReturnMovies() {
        Movie movie = new Movie();
        movie.setTitle("Interstellar");

        when(movieRepository.findAll(any(Sort.class)))
                .thenReturn(List.of(movie));

        List<Movie> movies = movieService.getAllMovies();

        assertEquals(1, movies.size());
        assertEquals("Interstellar", movies.get(0).getTitle());
    }

    @Test
    void saveMovie_shouldCallRepositorySave() {
        Movie movie = new Movie();
        movie.setTitle("Inception");

        movieService.saveMovie(movie);

        verify(movieRepository, times(1)).save(movie);
    }
}
