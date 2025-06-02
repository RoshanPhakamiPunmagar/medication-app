
package com.example.medicationapp.model

import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Entity class representing a reminder for a client's medication in the Room database.
 * Each reminder is linked to a specific ClientMedication via a foreign key.
 * Stores the time the reminder should trigger and the type of reminder.
 * Index on clientMedicationId for efficient querying.
 */


data class Reminder(
    val reminderId: Long = 0,

    val clientMedicationId: Long,

    val reminderTime: LocalTime,

    val reminderType: String
)
