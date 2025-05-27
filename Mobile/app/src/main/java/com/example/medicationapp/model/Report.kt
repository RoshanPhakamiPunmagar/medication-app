package com.example.medicationapp.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime


data class Report @RequiresApi(Build.VERSION_CODES.O) constructor(
    val reportId: Long,

    val carerId: Int,
    val clientId: Long,

    val notes: String,

    val dateCreated: LocalDateTime = LocalDateTime.now()
)
