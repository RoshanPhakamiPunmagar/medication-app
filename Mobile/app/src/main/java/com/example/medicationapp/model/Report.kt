package com.example.medicationapp.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.*
import java.time.LocalDateTime

@Entity(
    tableName = "reports",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["carerId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Client::class,
            parentColumns = ["clientId"],
            childColumns = ["clientId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("carerId"), Index("clientId")]
)
data class Report @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey(autoGenerate = true)
    val reportId: Long = 0,

    val carerId: Int,
    val clientId: Long,

    val notes: String,

    val dateCreated: LocalDateTime = LocalDateTime.now()
)
