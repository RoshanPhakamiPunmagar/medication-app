package com.example.medicationapp.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "roles",
    indices = [Index(value = ["roleName"], unique = true)]
)
data class Role(
    @PrimaryKey(autoGenerate = true)
    val roleId: Long = 0,
    val roleName: String
)
