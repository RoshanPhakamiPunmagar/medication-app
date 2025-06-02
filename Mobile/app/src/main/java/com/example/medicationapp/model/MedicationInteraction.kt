

package com.example.medicationapp.model

/**
 * Entity class representing interactions between two medications in the Room database.
 * Stores the IDs of the two interacting medications, a description of the interaction, and its severity level.
 * Includes foreign key constraints to ensure both medications exist in the medications table.
 * Defines indices on both medication ID columns for optimized query performance.
 */


data class MedicationInteraction(
    val interactionId: Long = 0,

    val medication1Id: Long,

    val medication2Id: Long,

    val interactionDescription: String,
    val severity: Severity
) {
    enum class Severity {
        LOW, MEDIUM, HIGH, CRITICAL
    }
}
