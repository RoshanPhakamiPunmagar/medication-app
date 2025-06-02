// Amy Wickham 121785021
// File: ClientWithMedicationsDTO.java
// Description: DTO for representing a client and their list of medications.

package com.example.meditime.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object representing a client along with their list of assigned medications.
 *
 * Contains the client's ID, name, and a list of ClientMedicationDTO objects representing
 * the medications associated with the client.
 */

@Getter
@Setter
public class ClientWithMedicationsDTO {
    private Long clientId;
    private String clientName;
    private List<ClientMedicationDTO> medications;
}
