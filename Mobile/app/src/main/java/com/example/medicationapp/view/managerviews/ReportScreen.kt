package com.example.medicationapp.view.managerviews

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * A placeholder composable screen for reports.
 *
 * Displays a simple message indicating that the Report Screen feature
 * is under development and will be available soon.
 * Uses basic layout with padding and spacing.
 */


@Composable
fun ReportScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))
        Text("Report Screen coming soon...")
    }
}