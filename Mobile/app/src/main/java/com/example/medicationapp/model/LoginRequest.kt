

package com.example.medicationapp.model

/**
 * Data class representing a login request payload.
 * Contains the user's email and password used for authentication.
 */


data class LoginRequest(
    val email: String,
    val password: String
)
