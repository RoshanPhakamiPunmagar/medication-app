// Amy Wickham 12178502
package com.example.meditime.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

/**
 * Entity representing a log of medication adherence.
 * Tracks when a user (e.g., carer) checked the medication adherence
 * for a specific client medication and records the adherence rate.
 */
@Entity
@Getter
@Setter
public class AdherenceLog {

    // Primary key for the adherence log
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adherenceId;

    // The client medication associated with this adherence log
    @ManyToOne
    private ClientMedication clientMedication;

    // The user (e.g., carer) who logged the adherence
    @ManyToOne
    private User user;

    // The specific medication log entry linked to this adherence (optional)
    @ManyToOne
    @JoinColumn(name = "medication_log_id")
    private MedicationLog medicationLog;

    // The time at which adherence was checked
    private LocalTime checkedTime;

    // Adherence rate recorded at the check time (e.g., percentage or ratio)
    private Double adherenceRate;
}
