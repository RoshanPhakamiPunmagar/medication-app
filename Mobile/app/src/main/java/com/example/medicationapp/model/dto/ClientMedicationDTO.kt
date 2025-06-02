package com.example.medicationapp.model.dto

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

/**
 * Data Transfer Object (DTO) representing a client's medication information.
 *
 * Used to transfer combined medication and client details, typically between layers of the app
 * (e.g., from backend to UI or between components).
 *
 * Fields:
 * - clientId: ID of the client prescribed the medication.
 * - medicationId: ID of the medication.
 * - dosage: Dosage instructions (e.g., "2 tablets daily").
 * - clientName: Full name of the client.
 * - medicationName: Name of the medication.
 * - startDate: Date when the medication regimen begins.
 * - endDate: Date when the medication regimen ends.
 * - isPaused: Indicates if the medication is currently paused.
 * - scheduledTimes: List of times the medication should be taken daily.
 *
 * Implements Serializable for easy data passing (e.g., through Intents or Bundles).
 */

data class ClientMedicationDTO(
    val clientId: Long,
    val medicationId: Long,
    val dosage: String,
    val clientName: String,
    val medicationName: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val isPaused: Boolean,
    val scheduledTimes: List<LocalTime>
): Serializable