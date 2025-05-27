package com.example.medicationapp.model


import java.time.LocalDateTime
import java.time.LocalTime

data class Reminder(
    val reminderId: Long,

    val clientMedicationId: Long,

    val reminderTime: LocalTime,

    val reminderType: String
)
