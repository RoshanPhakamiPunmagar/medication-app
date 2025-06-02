
package com.example.medicationapp.model

import java.time.LocalDate
import java.time.LocalTime
import java.io.Serializable

/**Defines the ClientMedication entity representing the relationship between clients and their medications.
Each entry links a client to a specific medication, with details like dosage, start/end dates, and scheduled times.
This entity uses Room annotations to define table structure, foreign key relationships, and indexing.
Parcelable implementation allows ClientMedication instances to be passed between Android components.
 **/



data class ClientMedication(
    val clientMedicationId: Long,
    val clientId: Long,
    val medicationId: Long,
    val dosage: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val isPaused: Boolean = false,
    val scheduledTimes: List<LocalTime>
) : Serializable
