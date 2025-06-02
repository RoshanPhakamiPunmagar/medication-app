package com.example.meditime.service;

import com.example.meditime.model.User;
import com.example.meditime.repository.UserRepository;
import com.example.meditime.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of Spring Security's UserDetailsService.
 *
 * Loads user-specific data during authentication by email.
 * Retrieves a User entity from the database via UserRepository,
 * then converts it into a UserDetails object (CustomUserDetails).
 *
 * Throws UsernameNotFoundException if no user is found with the given email.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new CustomUserDetails(user);
    }


}