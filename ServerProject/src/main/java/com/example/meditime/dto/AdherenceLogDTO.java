// Amy Wickham 121785021
// File: AdherenceLogDTO.java
// Description: Data Transfer Object (DTO) for representing adherence log information in a simplified, serializable form
//              for client-side or REST API use. Part of the MediTime medication management system.

package com.example.meditime.dto;

import lombok.Data;
import com.example.meditime.model.AdherenceLog;

/**
 * A DTO (Data Transfer Object) that simplifies the AdherenceLog entity for use in APIs and front-end communication.
 * Only essential information is included to reduce payload size and increase clarity.
 */
@Data // Lombok annotation to automatically generate getters, setters, toString, equals, and hashCode
public class AdherenceLogDTO {

    // Unique identifier of the adherence log entry
    private Long adherenceId;

    // Full name of the user (e.g. carer or manager) who created or is associated with the log
    private String userFullName;

    // Name of the medication associated with the adherence log
    private String clientMedicationName;

    // Timestamp when the adherence was checked or recorded (as a String for easy JSON serialization)
    private String checkedTime;

    // Adherence rate (e.g. percentage of medication taken on time)
    private Double adherenceRate;

    /**
     * Converts an AdherenceLog entity into an AdherenceLogDTO.
     * This helps decouple the internal data model from what is exposed to the client.
     *
     * @param log the AdherenceLog entity to convert
     * @return a populated AdherenceLogDTO, or null if the input is null
     */
    public static AdherenceLogDTO fromEntity(AdherenceLog log) {
        if (log == null) return null;

        AdherenceLogDTO dto = new AdherenceLogDTO();
        dto.setAdherenceId(log.getAdherenceId());
        dto.setUserFullName(log.getUser().getName());
        dto.setClientMedicationName(log.getClientMedication().getMedication().getName());
        dto.setCheckedTime(log.getCheckedTime().toString());
        dto.setAdherenceRate(log.getAdherenceRate());

        return dto;
    }
}
