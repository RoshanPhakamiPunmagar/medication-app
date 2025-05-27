// Amy Wickham 121785021
// File: ClientMedicationDTO.java
// Description: DTO for assigning and retrieving client-medication data.

package com.example.meditime.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Data Transfer Object (DTO) for ClientMedication entity.
 * Used for transferring data about a client's medication assignments,
 * including scheduling and status.
 */
public class ClientMedicationDTO {

    // Unique identifier for the client
    @Setter
    @Getter
    private Long clientId;

    // Unique identifier for the specific client-medication relationship
    @Setter
    @Getter
    private Long clientMedicationId;

    // Unique identifier for the medication
    @Setter
    @Getter
    private Long medicationId;

    // Dosage instructions (e.g., "500 mg twice a day")
    @Getter
    @Setter
    private String dosage;

    // Name of the client (for convenience in responses)
    @Getter
    @Setter
    private String clientName;

    // Name of the medication (for convenience in responses)
    @Getter
    @Setter
    private String medicationName;

    // Start date for the medication schedule
    @Setter
    @Getter
    private LocalDate startDate;

    // End date for the medication schedule
    @Getter
    @Setter
    private LocalDate endDate;

    // Indicates whether this medication assignment is currently paused
    private boolean isPaused = false;

    // List of scheduled times for medication intake (e.g., 08:00, 20:00)
    @Setter
    @Getter
    private List<LocalTime> scheduledTimes;

    /**
     * -- GETTER --
     *  Getter for the list of medications.
     *  Useful when including nested medication information.
     *
     *
     * -- SETTER --
     *  Setter for the list of medications.
     *
     @return List of ClientMedicationDTO objects
      * @param medications list of ClientMedicationDTO objects
     */
    // List of other medications assigned to the client (nested DTOs)
    @Setter
    @Getter
    private List<ClientMedicationDTO> medications;

    /**
     * Checks if the medication is currently paused.
     * @return true if paused, false otherwise
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Sets the paused status of the medication.
     * @param paused true to pause, false to resume
     */
    public void setPaused(boolean paused) {
        isPaused = paused;
    }
}
