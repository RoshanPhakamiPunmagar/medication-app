// Amy Wickham 12178502
// File: Reminder.java
// Description: This entity represents a reminder associated with a client medication. It stores the scheduled time and type of reminder.

package com.example.meditime.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Getter
@Setter // Lombok automatically generates getters and setters for all fields
public class Reminder {

    // Primary key for the Reminder entity, auto-generated
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reminderId;

    // Many-to-one relationship: each reminder is linked to one client medication
    @ManyToOne
    private ClientMedication clientMedication;

    // The specific time when the reminder should trigger
    private LocalTime reminderTime;

    // The type of reminder (e.g., notification, alert, email, etc.)
    private String reminderType;

}
