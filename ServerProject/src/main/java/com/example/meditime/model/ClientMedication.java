// Amy Wickham 12178502
package com.example.meditime.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Entity representing the medication prescribed to a client.
 * Tracks details about the medication, dosage, schedule, and status.
 */
@Entity
@Getter
@Setter
public class ClientMedication {

    /**
     * Primary key for the client medication record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientMedicationId;

    /**
     * The client to whom this medication is assigned.
     */
    @ManyToOne
    private Client client;

    /**
     * The medication prescribed to the client.
     */
    @ManyToOne
    private Medication medication;

    /**
     * Dosage information for the medication (e.g., "10 mg once daily").
     */
    private String dosage;

    /**
     * The start date when the medication regimen begins.
     */
    private LocalDate startDate;

    /**
     * The end date when the medication regimen ends.
     */
    private LocalDate endDate;

    /**
     * Flag indicating whether the medication is currently paused.
     */
    private boolean isPaused;

    /**
     * Scheduled times of day when the medication should be taken.
     * Stored as a collection of LocalTime objects.
     */
    @ElementCollection
    private List<LocalTime> scheduledTimes;

}
