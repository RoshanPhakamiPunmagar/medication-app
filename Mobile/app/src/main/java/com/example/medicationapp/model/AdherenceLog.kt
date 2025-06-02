

package com.example.medicationapp.model

import java.time.LocalDateTime

/**
 * Entity representing a log of medication adherence by a user.
 *
 * - Logs are linked to a specific medication (ClientMedication) and a user (User).
 * - Stores the time when adherence was checked and the adherence rate as a percentage (0.0 to 1.0).
 * - On deletion of the related medication or user, associated logs are automatically deleted (CASCADE).
 * - Indexed on clientMedicationId and userId to optimize queries filtering by these columns.
 */


data class AdherenceLog(
    val adherenceId: Long,

    val clientMedicationId: Long,
    val userId: Long,
    val checkedTime: LocalDateTime,
    val adherenceRate: Double
)
