package com.example.medicationapp.model.dto

import java.time.LocalTime

data class MedicationLogDTO(
    val clientMedicationId: Long,
    val carerId: Long,
    val scheduledTime: String,     // Use "HH:mm:ss" format
    val actualTime: String?,       // Nullable, same format
    val status: String,            // Send enum as string
    val notes: String?
)