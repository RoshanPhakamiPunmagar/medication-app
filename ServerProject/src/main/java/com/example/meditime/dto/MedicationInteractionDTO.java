//Amy Wickham 121785021

// File: MedicationInteractionDTO.java


package com.example.meditime.dto;

import com.example.meditime.model.Medication;
import lombok.Data;
import com.example.meditime.model.MedicationInteraction;
/**
 * Data Transfer Object representing an interaction between two medications.
 *
 * Contains details about the interaction, including the involved medications,
 * a description of the interaction, and its severity level.
 *
 * Provides a static method to create a DTO instance from a MedicationInteraction entity.
 */
@Data
public class MedicationInteractionDTO {
    private Long interactionId;
    private Medication medicationA;
    private Medication medicationB;
    private String interactionDescription;
    private String severity;

    public static MedicationInteractionDTO fromEntity(MedicationInteraction interaction) {
        if (interaction == null) return null;
        MedicationInteractionDTO dto = new MedicationInteractionDTO();
        dto.setInteractionId(interaction.getInteractionId());
        dto.setMedicationA(interaction.getMedication1());
        dto.setMedicationB(interaction.getMedication2());
        dto.setInteractionDescription(interaction.getInteractionDescription());
        dto.setSeverity(interaction.getSeverity().name());
        return dto;
    }
}