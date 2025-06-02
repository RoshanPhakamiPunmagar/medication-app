package com.example.meditime.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Utility class for handling JSON Web Tokens (JWT) used for authentication.
 *
 * Responsibilities include:
 * - Generating JWT tokens containing the user's email as the subject.
 * - Validating incoming JWT tokens to ensure they are correctly signed and not expired.
 * - Extracting the user's email (subject) from a valid JWT token.
 *
 * This class uses a secret key to sign tokens and verifies tokens using the same key.
 * Tokens are set to expire after 24 hours (configurable).
 *
 * Note: The secret key used here is hardcoded for demonstration purposes and should be securely managed in production.
 */
@Component
public class JwtUtil {
    // Secret key for signing JWTs
    private final String SECRET_KEY = "meditime_secret_key"; // Change in production!
    // Token expiration time in milliseconds
    private final long EXPIRATION = 86400000; // 24 hours

    // Generate a JWT token containing the user's email as subject
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
