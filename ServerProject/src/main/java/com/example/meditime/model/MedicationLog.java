//Amy Wickham 121785021
package com.example.meditime.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
/**
 * Entity class representing a log entry for medication administration.
 * Tracks the scheduled and actual times the medication was given, the status of administration,
 * notes, and the carer responsible for administering the medication.
 */
@Entity
public class MedicationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne
    private ClientMedication clientMedication;

    @ManyToOne
    //@JoinColumn(name = "carer_id")  // optional: maps to DB column name
    private User carer;  // this is the "carerId" from the diagram

    private String scheduledTime;
    private String actualTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String notes;

    public enum Status {
        Given, Skipped, Missed, Late
    }

    // Getters and Setters

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public ClientMedication getClientMedication() {
        return clientMedication;
    }

    public void setClientMedication(ClientMedication clientMedication) {
        this.clientMedication = clientMedication;
    }

    public User getCarer() {
        return carer;
    }

    public void setCarer(User carer) {
        this.carer = carer;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
