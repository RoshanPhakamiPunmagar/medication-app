//Amy Wickham 12178502
package com.example.meditime.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class AdherenceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adherenceId;

    @ManyToOne
    private ClientMedication clientMedication;

    @ManyToOne
    private User user;

    private LocalTime checkedTime;
    private Double adherenceRate;

    // Getters and Setters

    @ManyToOne
    @JoinColumn(name= "medication_log_id")
    private MedicationLog medicationLog;

    public Long getAdherenceId() {
        return adherenceId;
    }

    public void setAdherenceId(Long adherenceId) {
        this.adherenceId = adherenceId;
    }

    public ClientMedication getClientMedication() {
        return clientMedication;
    }

    public void setClientMedication(ClientMedication clientMedication) {
        this.clientMedication = clientMedication;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MedicationLog getMedicationLog() {
        return medicationLog;
    }

    public void setMedicationLog(MedicationLog medicationLog) {
        this.medicationLog = medicationLog;
    }

    public LocalTime getCheckedTime() {
        return checkedTime;
    }

    public void setCheckedTime(LocalTime checkedTime) {
        this.checkedTime = checkedTime;
    }

    public Double getAdherenceRate() {
        return adherenceRate;
    }

    public void setAdherenceRate(Double adherenceRate) {
        this.adherenceRate = adherenceRate;
    }

}