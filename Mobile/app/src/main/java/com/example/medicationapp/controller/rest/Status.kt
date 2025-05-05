package com.example.medicationapp.controller.rest

//class Status {
//    var status: String = ""
//    var role: String = ""
//    var userId: Long = 0L
//
//    fun fetchStatus(): String {
//        return status
//    }
//}


data class Status(
    val status: String,
    val role: String,
    val userId: Long
)

