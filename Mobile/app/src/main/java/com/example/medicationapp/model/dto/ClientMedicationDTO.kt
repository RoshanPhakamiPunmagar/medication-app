package com.example.medicationapp.model.dto

import java.time.LocalDate
import java.time.LocalTime

data class ClientMedicationDTO(
    val clientId: Long,
    val medicationId: Long,
    val dosage: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val isPaused: Boolean,
    val scheduledTimes: List<LocalTime>
)