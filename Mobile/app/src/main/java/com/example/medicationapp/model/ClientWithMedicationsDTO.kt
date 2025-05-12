package com.example.medicationapp.model

data class ClientWithMedicationsDTO(
    val clientId: Long,
    val clientName: String,
    val medications: List<ClientMedication>
)
