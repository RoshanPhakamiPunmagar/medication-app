package com.example.meditime.model;

import lombok.Getter;
import lombok.Setter;
/**
 * Represents the payload for a user login request.
 * Contains the user's email and password credentials.
 */

public class LoginRequest {
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String password;
    // getters and setters
}
