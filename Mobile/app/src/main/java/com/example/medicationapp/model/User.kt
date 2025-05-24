package com.example.medicationapp.model



data class User(
    val userId: Long? = null,
    val name: String,
    val email: String,
    val password: String,
    val roleId: Long
)
