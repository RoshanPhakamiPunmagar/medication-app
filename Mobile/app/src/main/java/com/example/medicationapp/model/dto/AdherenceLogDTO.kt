package com.example.medicationapp.model.dto

/**
 * Data Transfer Object (DTO) for representing adherence log details in a user-friendly format.
 *
 * Typically used for displaying or transmitting adherence data to the UI or across layers.
 *
 * Fields:
 * - adherenceId: Unique identifier for the adherence log entry.
 * - userFullName: Full name of the user who recorded the adherence.
 * - clientMedicationName: Name of the medication associated with the adherence.
 * - checkedTime: Timestamp (as String) when the adherence was recorded.
 * - adherenceRate: Adherence percentage (e.g., 1.0 = 100%, 0.75 = 75%).
 * - scheduledTime: Scheduled time for taking the medication (as String).
 * - actualTime: Actual time the medication was taken (as String).
 * - status: Status of adherence (e.g., "On Time", "Late", "Missed").
 * - notes: Additional notes or comments related to the log.
 */

data class AdherenceLogDTO(
    val adherenceId: Int,
    val userFullName: String,
    val clientMedicationName: String,
    val checkedTime: String,
    val adherenceRate: Double,
    val scheduledTime: String,
    val actualTime: String,
    val status: String,
    val notes: String
)