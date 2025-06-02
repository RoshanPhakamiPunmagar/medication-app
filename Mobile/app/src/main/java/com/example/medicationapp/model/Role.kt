

package com.example.medicationapp.model

/**
 * Entity class representing a user role in the Room database.
 * Stores a unique role name with an auto-generated primary key.
 * Includes a unique index on roleName to prevent duplicate roles.
 */

data class Role(
    val roleId: Long = 0,
    val roleName: String
)
