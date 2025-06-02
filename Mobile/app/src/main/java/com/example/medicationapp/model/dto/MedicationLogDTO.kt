package com.example.medicationapp.model.dto

import java.time.LocalTime

/**
 * MedicationLogDTO.kt
 *
 * Data Transfer Object used to log a medication administration event for a client.
 * This DTO is sent to the backend when a carer logs the administration or omission
 **/

data class MedicationLogDTO(
    val clientMedicationId: Long,
    val carerId: Long,
    val scheduledTime: String,     // Use "HH:mm:ss" format
    val actualTime: String?,       // Nullable, same format
    val status: String,            // Send enum as string
    val notes: String?
)