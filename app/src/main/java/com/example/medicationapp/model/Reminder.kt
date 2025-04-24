package com.example.medicationapp.model

import androidx.room.*
import java.time.LocalDateTime
import java.time.LocalTime

@Entity(
    tableName = "reminders",
    foreignKeys = [
        ForeignKey(
            entity = ClientMedication::class,
            parentColumns = ["clientMedicationId"],
            childColumns = ["clientMedicationId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("clientMedicationId")]
)
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val reminderId: Long = 0,

    val clientMedicationId: Long,

    val reminderTime: LocalTime,

    val reminderType: String
)
