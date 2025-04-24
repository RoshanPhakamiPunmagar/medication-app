package com.example.medicationapp.model

import androidx.room.*

@Entity(
    tableName = "medication_interactions",
    foreignKeys = [
        ForeignKey(
            entity = Medication::class,
            parentColumns = ["medicationId"],
            childColumns = ["medication_id_1"]
        ),
        ForeignKey(
            entity = Medication::class,
            parentColumns = ["medicationId"],
            childColumns = ["medication_id_2"]
        )
    ],
    indices = [
        Index("medication_id_1"),                // ← index both FKs
        Index("medication_id_2")
    ]
)
data class MedicationInteraction(
    @PrimaryKey(autoGenerate = true)
    val interactionId: Long = 0,

    @ColumnInfo(name = "medication_id_1")
    val medication1Id: Long,

    @ColumnInfo(name = "medication_id_2")
    val medication2Id: Long,

    val interactionDescription: String,
    val severity: Severity
) {
    enum class Severity {
        LOW, MEDIUM, HIGH, CRITICAL
    }
}
