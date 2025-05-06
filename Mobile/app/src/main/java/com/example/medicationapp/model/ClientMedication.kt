package com.example.medicationapp.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Entity(
    tableName = "client_medications",
    foreignKeys = [
        ForeignKey(
            entity = Client::class,
            parentColumns = ["clientId"],
            childColumns = ["clientId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Medication::class,
            parentColumns = ["medicationId"],
            childColumns = ["medicationId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("clientId"),
        Index("medicationId")
    ]
)
@Parcelize
data class ClientMedication(
    @PrimaryKey(autoGenerate = true)
    val clientMedicationId: Long = 0,
    val clientId: Long,
    val medicationId: Long,
    val dosage: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val isPaused: Boolean = false,
    val scheduledTimes: List<LocalTime>


):Parcelable
