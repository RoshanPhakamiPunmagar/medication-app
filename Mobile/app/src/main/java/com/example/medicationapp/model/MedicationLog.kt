

package com.example.medicationapp.model

import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Entity class representing a log entry for medication administration in the Room database.
 * Tracks when a medication was scheduled versus when it was actually given, who administered it, and its status.
 * Linked to both ClientMedication and User (carer) entities via foreign key constraints.
 * Includes optional notes and a status enum to indicate the outcome of the medication event.
 * Uses indices on foreign keys for optimized querying.
 */


data class MedicationLog(
    val logId: Long,

    val clientMedicationId: Long,
    val carerId: Long,

    val scheduledTime: List<LocalTime>,
    val actualTime: LocalTime? = null,

    val status: Status,
    val notes: String? = null
) {
    enum class Status {
        Given, Skipped, Missed, Late
    }
}
