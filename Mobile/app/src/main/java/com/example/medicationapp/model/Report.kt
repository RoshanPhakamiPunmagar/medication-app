package com.example.medicationapp.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

/**
 * Report.kt
 *
 * Data class representing a report submitted by a carer for a client.
 * This model is used to encapsulate notes or observations made by the carer,
 * along with metadata like the creation date and associated user IDs.
 **/


data class Report @RequiresApi(Build.VERSION_CODES.O) constructor(
    val reportId: Long = 0,

    val carerId: Int,
    val clientId: Long,

    val notes: String,

    val dateCreated: LocalDateTime = LocalDateTime.now()
)
