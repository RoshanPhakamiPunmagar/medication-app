package com.example.meditime.AdminPages;

import com.example.meditime.model.User;
import com.example.meditime.repository.UserRepository;
import com.example.meditime.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

/**
 * AuthApiController handles user authentication for the application.
 *
 * This REST controller provides an endpoint for user login. It verifies the user's credentials,
 * and upon successful authentication, generates and returns a JWT token along with the user's
 * name and role ID.
 *
 * Endpoint:
 * - POST /api/login : Authenticates a user using email and password.
 *
 * Dependencies:
 * - UserRepository: to retrieve user details from the database.
 * - JwtUtil: to generate JWT tokens.
 * - PasswordEncoder: to verify hashed passwords.
 */
@RestController
@RequestMapping("/api")
public class AuthApiController {

    @Autowired private UserRepository userRepository;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        String token = jwtUtil.generateToken(email);
        return Map.of(
                "token", token,
                "name", user.get().getName(),
                "role", user.get().getRoleId().toString()
        );
    }
}