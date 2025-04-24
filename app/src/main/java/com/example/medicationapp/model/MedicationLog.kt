package com.example.medicationapp.model

import androidx.room.*
import java.time.LocalDateTime

@Entity(
    tableName = "medication_logs",
    foreignKeys = [
        ForeignKey(
            entity = ClientMedication::class,
            parentColumns = ["clientMedicationId"],
            childColumns = ["client_medication_id"], // ✅ CORRECT
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["carerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["client_medication_id"]),
        Index(value = ["carerId"])
    ]
)

data class MedicationLog(
    @PrimaryKey(autoGenerate = true)
    val logId: Long = 0,

    @ColumnInfo(name = "client_medication_id")
    val clientMedicationId: Long,
    val carerId: Long,

    val scheduledTime: LocalDateTime,
    val actualTime: LocalDateTime? = null,

    val status: Status,
    val notes: String? = null
) {
    enum class Status {
        Given, Skipped, Missed, Late
    }
}
