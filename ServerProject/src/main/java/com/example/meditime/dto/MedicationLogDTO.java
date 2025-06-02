//Amy Wickham 121785021
// Amy Wickham 12178502
// File: MedicationLogDTO.java
// Description: See MediTime documentation. This file is part of the medication management system.

package com.example.meditime.dto;

import lombok.Data;
import com.example.meditime.model.MedicationLog;

import java.time.format.DateTimeParseException;
/**
 * Data Transfer Object representing a medication log entry.
 *
 * Contains information about a specific medication administration event,
 * including the associated client medication ID, carer ID, scheduled and actual times,
 * status, and any notes.
 *
 * Includes a static method to create a DTO from a MedicationLog entity.
 * Time fields are represented as Strings for easier JSON serialization and deserialization.
 */
@Data
public class MedicationLogDTO {
    private Long logId;
    private Long clientMedicationId;  // Just the ID
    private Long carerId;
    private String scheduledTime;  // Change from LocalTime to String
    private String actualTime;
    private String status;
    private String notes;

    public static MedicationLogDTO fromEntity(MedicationLog log) {
        if (log == null) return null;
        MedicationLogDTO dto = new MedicationLogDTO();
        dto.setLogId(log.getLogId());
        dto.setCarerId(log.getCarer().getUserId());
        dto.setStatus(log.getStatus().toString());
        dto.setScheduledTime(log.getScheduledTime());
        dto.setActualTime(log.getActualTime());
        return dto;
    }


    // Additional helper method for string-based time parsing
    public void setScheduledTimeFromString(String timeString) {
        try {
            this.scheduledTime = timeString;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format: " + timeString);
        }
    }

    public String setActualTimeFromString() {

        return    this.actualTime;

    }
    // Getters and Setters
    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getClientMedicationId() {
        return clientMedicationId;
    }

    public void setClientMedicationId(Long clientMedicationId) {
        this.clientMedicationId = clientMedicationId;
    }

    public Long getCarerId() {
        return carerId;
    }

    public void setCarerId(Long carerId) {
        this.carerId = carerId;
    }


    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getActualTime() {
        return actualTime;
    }

    public void setActualTime(String actualTime) {
        this.actualTime = actualTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}