package com.example.meditime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for representing client information.
 * Uses Lombok annotations to reduce boilerplate code.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    private Long clientId;     // Unique identifier for the client
    private String name;       // Full name of the client
    private String dob;        // Date of birth of the client
    private String contact;    // Contact information for the client
    private Long carerUserId;  // User ID of the carer assigned to the client
}
