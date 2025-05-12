package com.example.medicationapp.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "clients",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["carerId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [ Index("carerId") ]
)
data class Client(
    @PrimaryKey(autoGenerate = true)
    val clientId: Long = 0,

    val name: String,
    val dob: String,
    val contactInfo: String,
    var carerId: Long?
)