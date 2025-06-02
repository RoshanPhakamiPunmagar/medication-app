
package com.example.medicationapp.model

/**
 * Entity class representing a medication in the Room database.
 * Includes fields for medication name, description, side effects, and interaction information.
 * Each medication has a unique auto-generated ID as the primary key.
 */



data class Medication(
    val medicationId: Int = 0,
    val name: String,
    val description: String,
    val sideEffects: String,
    val interactionInfo: String
)
