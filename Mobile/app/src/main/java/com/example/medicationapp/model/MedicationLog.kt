package com.example.medicationapp.model

import java.time.LocalDateTime
import java.time.LocalTime


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
