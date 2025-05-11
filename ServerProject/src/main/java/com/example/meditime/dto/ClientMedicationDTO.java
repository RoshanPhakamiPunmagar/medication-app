// Amy Wickham 121785021
// File: ClientMedicationDTO.java
// Description: DTO for assigning and retrieving client-medication data.

package com.example.meditime.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ClientMedicationDTO {

    // Getters and Setters
    @Setter
    @Getter
    private Long clientId;
    @Setter
    @Getter
    private Long medicationId;
    @Setter
    @Getter
    private String medicationName;
    @Getter
    @Setter
    private String dosage;
    @Setter
    @Getter
    private LocalDate startDate;
    @Getter
    @Setter
    private LocalDate endDate;
    private boolean isPaused = false;
    @Setter
    @Getter
    private List<LocalTime> scheduledTimes;

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

}
