package com.example.medicationapp.model


data class Medication(
    val medicationId: Int,
    val name: String,
    val description: String,
    val sideEffects: String,
    val interactionInfo: String
)
