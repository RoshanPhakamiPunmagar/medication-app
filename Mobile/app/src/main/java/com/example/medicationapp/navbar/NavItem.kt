package com.example.medicationapp.navbar

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Data class representing a navigation item for a UI navbar.
 * Contains a label for display and an icon represented by an ImageVector.
 */


data class NavItem (
    val label: String,
    val icon: ImageVector
)