package com.Test.mts.service;

import com.MovieTicket.MovieBooking.model.Show;
import com.MovieTicket.MovieBooking.repository.ShowRepository;
import com.MovieTicket.MovieBooking.service.BookingService;
import com.MovieTicket.MovieBooking.service.ShowService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShowServiceTest {

    @Mock
    private ShowRepository showRepository;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private ShowService showService;

    @Test
    void getAllShows_shouldReturnShows() {
        Show show = new Show();

        when(showRepository.findAll()).thenReturn(List.of(show));

        List<Show> shows = showService.getAllShows();

        assertEquals(1, shows.size());
    }

    @Test
    void getShowById_shouldReturnShow() {
        Show show = new Show();

        when(showRepository.findById(1L)).thenReturn(Optional.of(show));

        Show result = showService.getShowById(1L);

        assertNotNull(result);
    }

    @Test
    void getShowsByMovieId_shouldReturnShows() {
        Show show = new Show();

        when(showRepository.findByMovieId(10L)).thenReturn(List.of(show));

        List<Show> shows = showService.getShowsByMovieId(10L);

        assertEquals(1, shows.size());
    }

    @Test
    void getTotalShowsCount_shouldReturnCount() {
        when(showRepository.count()).thenReturn(5L);

        long count = showService.getTotalShowsCount();

        assertEquals(5L, count);
    }

    @Test
    void getShowsByGenre_shouldReturnShows() {
        Show show = new Show();

        when(showRepository.findShowsByMovieGenre("Action"))
                .thenReturn(List.of(show));

        List<Show> shows = showService.getShowsByGenre("Action");

        assertEquals(1, shows.size());
    }

    @Test
    void deleteShow_shouldDeleteBookingsAndShow() {
        doNothing().when(bookingService).deleteByShowId(1L);
        doNothing().when(showRepository).deleteById(1L);

        showService.deleteShow(1L);

        verify(bookingService, times(1)).deleteByShowId(1L);
        verify(showRepository, times(1)).deleteById(1L);
    }
}
