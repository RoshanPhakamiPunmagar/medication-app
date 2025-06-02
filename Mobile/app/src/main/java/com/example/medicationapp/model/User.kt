
package com.example.medicationapp.model

/**
 * Entity class representing a user in the Room database.
 * Each user has a unique ID, name, email, password, and an associated role.
 * The roleId is a foreign key referencing the Role entity, with cascade behavior to set null on deletion.
 * An index is created on roleId for optimized queries.
 */


data class User(
    val userId: Long? = null,
    val name: String,
    val email: String,
    val password: String,
    val roleId: Long
)
