package com.example.medicationapp.model

import java.time.LocalDate
import java.time.LocalTime
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import java.io.Serializable

data class ClientMedication(
    val clientMedicationId: Long,
    val clientId: Long,
    val medicationId: Long,
    val dosage: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val isPaused: Boolean = false,
    val scheduledTimes: List<LocalTime>
) : Serializable
