package com.example.medicationapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medications")
data class Medication(
    @PrimaryKey(autoGenerate = true)
    val medicationId: Int = 0,
    val name: String,
    val description: String,
    val sideEffects: String,
    val interactionInfo: String
)
