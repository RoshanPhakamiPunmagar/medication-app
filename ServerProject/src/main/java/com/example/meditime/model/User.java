// Amy Wickham 12178502
// File: User.java
// Description: Entity representing a user in the MediTime system, including personal details and associated role.

package com.example.meditime.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a system user, such as a carer or admin, within the MediTime application.
 */
@Entity
@Getter  // Lombok annotation to generate all getters
@Setter  // Lombok annotation to generate all setters
public class User {

    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /**
     * The full name of the user.
     */
    private String name;

    /**
     * The email address of the user. Used for login and communication.
     */
    private String email;

    /**
     * The user's password. Should be stored securely (e.g., hashed).
     */
    private String password;

    /**
     * The ID of the role assigned to this user. Typically references a Role entity.
     */
    @Column(name = "role_id")
    private Long roleId;

    // No manual getters and setters are needed due to Lombok annotations.
}
