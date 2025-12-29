package com.MovieTicket.MovieBooking.service;

import com.MovieTicket.MovieBooking.exception.GlobalExceptionHandler;
import com.MovieTicket.MovieBooking.model.User;
import com.MovieTicket.MovieBooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new GlobalExceptionHandler.UserNotFoundException(
                                "User not found with id: " + id
                        )
                );
    }

    public User saveUser(User user) {
        if (user.getId() == null) {
            user.setCreatedAt(LocalDateTime.now());
        }

        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        } else {
            user.setRole(user.getRole().toUpperCase());
        }

        return userRepository.save(user);
    }

    public String updateUserProfile(User updatedUser,
                                    MultipartFile imageFile,
                                    Principal principal,
                                    RedirectAttributes redirectAttributes) {

        User existingUser = userRepository.findByEmail(principal.getName());

        if (existingUser == null) {
            throw new GlobalExceptionHandler.UserNotFoundException(
                    "User not found with email: " + principal.getName()
            );
        }

        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }

        if (updatedUser.getPhone() != null) {
            existingUser.setPhone(updatedUser.getPhone());
        }

        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            if (!updatedUser.getPassword().startsWith("$2a$")) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            } else {
                existingUser.setPassword(updatedUser.getPassword());
            }
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                existingUser.setProfileImage(imageFile.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload profile image");
            }
        }

        userRepository.save(existingUser);
        redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");

        return "redirect:/user/profile?success=true";
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new GlobalExceptionHandler.UserNotFoundException(
                    "User not found with id: " + id
            );
        }
        userRepository.deleteById(id);
    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new GlobalExceptionHandler.UserNotFoundException(
                    "User not found with email: " + email
            );
        }
        return user;
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public long getTotalUsers() {
        return userRepository.count();
    }

    public void updateLastLogin(User user) {
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }
}
