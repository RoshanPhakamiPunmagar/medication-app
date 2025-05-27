package com.example.medicationapp.model



data class MedicationInteraction(
    val interactionId: Long,

    val medication1Id: Long,

    val medication2Id: Long,

    val interactionDescription: String,
    val severity: Severity
) {
    enum class Severity {
        LOW, MEDIUM, HIGH, CRITICAL
    }
}
