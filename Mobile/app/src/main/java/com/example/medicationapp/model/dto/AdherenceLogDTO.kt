package com.example.medicationapp.model.dto

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