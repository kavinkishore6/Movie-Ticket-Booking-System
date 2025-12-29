package com.Test.mts.service;

import com.MovieTicket.MovieBooking.exception.GlobalExceptionHandler;
import com.MovieTicket.MovieBooking.model.User;
import com.MovieTicket.MovieBooking.repository.UserRepository;
import com.MovieTicket.MovieBooking.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void getAllUsers_shouldReturnUserList() {
        when(userRepository.findAll()).thenReturn(List.of(new User()));

        List<User> users = userService.getAllUsers();

        assertEquals(1, users.size());
        verify(userRepository).findAll();
    }

    @Test
    void getUserById_shouldReturnUser_whenExists() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getUserById_shouldThrowException_whenNotExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                GlobalExceptionHandler.UserNotFoundException.class,
                () -> userService.getUserById(1L)
        );
    }

    @Test
    void saveUser_shouldEncodePasswordAndSetRole() {
        User user = new User();
        user.setPassword("plain123");

        when(passwordEncoder.encode("plain123")).thenReturn("encoded123");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User savedUser = userService.saveUser(user);

        assertEquals("encoded123", savedUser.getPassword());
        assertEquals("USER", savedUser.getRole());
        assertNotNull(savedUser.getCreatedAt());
    }

    @Test
    void deleteUser_shouldDelete_whenExists() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_shouldThrowException_whenNotExists() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(
                GlobalExceptionHandler.UserNotFoundException.class,
                () -> userService.deleteUser(1L)
        );
    }

    @Test
    void findByEmail_shouldReturnUser() {
        User user = new User();
        user.setEmail("test@mail.com");

        when(userRepository.findByEmail("test@mail.com")).thenReturn(user);

        User result = userService.findByEmail("test@mail.com");

        assertEquals("test@mail.com", result.getEmail());
    }

    @Test
    void findByEmail_shouldThrowException_whenNotFound() {
        when(userRepository.findByEmail("x@mail.com")).thenReturn(null);

        assertThrows(
                GlobalExceptionHandler.UserNotFoundException.class,
                () -> userService.findByEmail("x@mail.com")
        );
    }

    @Test
    void existsByEmail_shouldReturnTrue() {
        when(userRepository.existsByEmail("test@mail.com")).thenReturn(true);

        assertTrue(userService.existsByEmail("test@mail.com"));
    }

    @Test
    void getTotalUsers_shouldReturnCount() {
        when(userRepository.count()).thenReturn(5L);

        assertEquals(5L, userService.getTotalUsers());
    }

    @Test
    void updateLastLogin_shouldUpdateTimestamp() {
        User user = new User();

        userService.updateLastLogin(user);

        assertNotNull(user.getLastLogin());
        verify(userRepository).save(user);
    }
}
