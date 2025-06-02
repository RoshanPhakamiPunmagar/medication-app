

package com.example.medicationapp.model
/**
 * Data class representing a status with optional associations to a user and a role.
 * Contains a status message and optionally links to userId and roleId.
 */


data class Status(
    val status: String,
    val userId: Long? = null,
    val roleId: Long? = null,
)