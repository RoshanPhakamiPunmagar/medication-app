package com.example.medicationapp.model

import java.time.LocalDateTime


data class AdherenceLog(
    val adherenceId: Long,

    val clientMedicationId: Long,
    val userId: Long,
    val checkedTime: LocalDateTime,
    val adherenceRate: Double
)
