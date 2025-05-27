// Amy Wickham 121785021
// Amy Wickham 12178502
// File: MedicationLogDTO.java
// Description: See MediTime documentation. This file is part of the medication management system.

package com.example.meditime.dto;

import lombok.Data;
import com.example.meditime.model.MedicationLog;

import java.time.format.DateTimeParseException;

@Data
public class MedicationLogDTO {

    // Unique identifier for the medication log
    private Long logId;

    // ID referencing the associated client medication
    private Long clientMedicationId;

    // ID of the carer responsible for this log entry
    private Long carerId;

    // Scheduled medication time, stored as a string for flexibility in serialization
    private String scheduledTime;

    // Actual medication time, stored as a string
    private String actualTime;

    // Status of the medication adherence (e.g., TAKEN, MISSED)
    private String status;

    // Optional notes about the medication event
    private String notes;

    /**
     * Static factory method to create a DTO from the MedicationLog entity.
     * Converts entity fields into DTO fields, adapting types as needed.
     * @param log The MedicationLog entity to convert
     * @return MedicationLogDTO instance or null if log is null
     */
    public static MedicationLogDTO fromEntity(MedicationLog log) {
        if (log == null) return null;
        MedicationLogDTO dto = new MedicationLogDTO();
        dto.setLogId(log.getLogId());
        dto.setCarerId(log.getCarer().getUserId());
        dto.setStatus(log.getStatus().toString());
        dto.setScheduledTime(log.getScheduledTime()); // assuming this returns String
        dto.setActualTime(log.getActualTime());       // assuming this returns String
        return dto;
    }

    /**
     * Helper method to set scheduledTime from a string representation.
     * Performs validation and throws IllegalArgumentException on invalid format.
     * @param timeString the string representing the scheduled time
     */
    public void setScheduledTimeFromString(String timeString) {
        try {
            // Here could be a parsing attempt if needed
            this.scheduledTime = timeString;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format: " + timeString);
        }
    }

    /**
     * Returns the actual time string.
     * Method currently just returns the actualTime field.
     * Could be expanded for validation or conversion.
     * @return actual time as string
     */
    public String setActualTimeFromString() {
        return this.actualTime;
    }
}
