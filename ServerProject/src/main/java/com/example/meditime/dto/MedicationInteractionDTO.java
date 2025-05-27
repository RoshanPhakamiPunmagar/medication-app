// Amy Wickham 121785021
// File: MedicationInteractionDTO.java

package com.example.meditime.dto;

import com.example.meditime.model.Medication;
import lombok.Data;
import com.example.meditime.model.MedicationInteraction;

/**
 * Data Transfer Object (DTO) for MedicationInteraction entity.
 * Used to transfer details about interactions between two medications,
 * including description and severity level.
 */
@Data
public class MedicationInteractionDTO {

    // Unique identifier for the interaction record
    private Long interactionId;

    // The first medication involved in the interaction
    private Medication medicationA;

    // The second medication involved in the interaction
    private Medication medicationB;

    // Description of the interaction between the two medications
    private String interactionDescription;

    // Severity level of the interaction (e.g., mild, moderate, severe)
    private String severity;

    /**
     * Converts a MedicationInteraction entity to a MedicationInteractionDTO.
     * Returns null if the provided entity is null.
     *
     * @param interaction the MedicationInteraction entity
     * @return MedicationInteractionDTO representing the entity data
     */
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
