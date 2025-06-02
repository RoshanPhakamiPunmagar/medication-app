package com.example.medicationapp.model

data class Client(
    val clientId: Long,

    val name: String,
    val dob: String,
    val contact: String,
    var carerId: Long?
)