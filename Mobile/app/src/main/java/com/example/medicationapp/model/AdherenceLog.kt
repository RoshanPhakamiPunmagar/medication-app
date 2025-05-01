package com.example.medicationapp.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime


@Entity(
    tableName = "adherence_logs",
    foreignKeys = [
        ForeignKey(
            entity = ClientMedication::class,
            parentColumns = ["clientMedicationId"],
            childColumns = ["clientMedicationId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("clientMedicationId"),
        Index("userId")
    ]
)
data class AdherenceLog(
    @PrimaryKey(autoGenerate = true)
    val adherenceId: Long = 0,

    val clientMedicationId: Long,
    val userId: Long,
    val checkedTime: LocalDateTime,
    val adherenceRate: Double
)
