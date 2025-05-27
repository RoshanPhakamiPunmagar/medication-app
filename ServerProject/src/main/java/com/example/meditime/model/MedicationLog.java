// Amy Wickham 121785021
package com.example.meditime.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing a log entry for a medication administration event.
 * Tracks scheduled vs actual administration times, status, and notes.
 */
@Entity
@Getter
@Setter
public class MedicationLog {

    /**
     * Primary key for the medication log entry.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    /**
     * The ClientMedication associated with this log entry.
     */
    @ManyToOne
    private ClientMedication clientMedication;

    /**
     * The carer (user) responsible for administering the medication.
     */
    @ManyToOne
    // @JoinColumn(name = "carer_id")  // optional: custom DB column name mapping
    private User carer;

    /**
     * The scheduled time for medication administration, stored as a string.
     */
    private String scheduledTime;

    /**
     * The actual time medication was administered, stored as a string.
     */
    private String actualTime;

    /**
     * Status indicating how the medication administration went.
     */
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * Additional notes related to this log entry.
     */
    private String notes;

    /**
     * Enumeration of possible medication administration statuses.
     */
    public enum Status {
        Given, Skipped, Missed, Late
    }

}
