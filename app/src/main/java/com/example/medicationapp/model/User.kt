package com.example.medicationapp.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    foreignKeys = [
        ForeignKey(
            entity = Role::class,
            parentColumns = ["roleId"],
            childColumns = ["roleId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("roleId")]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Long = 0,
    val name: String,
    val email: String,
    val password: String,
    val roleId: Long
)
