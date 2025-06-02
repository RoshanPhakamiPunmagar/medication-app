
package com.example.medicationapp.model

/**
 * Data Transfer Object (DTO) representing a client along with their associated medications.
 * Used for passing combined client and medication data between application layers or components.
 * Contains the client's ID, name, and a list of ClientMedication instances.
 */



data class ClientWithMedicationsDTO(
    val clientId: Long,
    val clientName: String,
    val medications: List<ClientMedication>
)
