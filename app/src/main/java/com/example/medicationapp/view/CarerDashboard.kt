package com.example.medicationapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CarerDashboard(
    onNavigateToReminders: () -> Unit,
    onNavigateToClientSelection: () -> Unit,
    onNavigateToIncidentNotes: () -> Unit,
    onNavigateToOfflineMode: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Carer Dashboard", style = MaterialTheme.typography.headlineMedium)

        Button(onClick = onNavigateToClientSelection, modifier = Modifier.fillMaxWidth()) {
            Text("Start Shift / Select Client")
        }

        Button(onClick = onNavigateToReminders, modifier = Modifier.fillMaxWidth()) {
            Text("View Medication Reminders")
        }

        Button(onClick = onNavigateToIncidentNotes, modifier = Modifier.fillMaxWidth()) {
            Text("Upload Incident Notes")
        }

        Button(onClick = onNavigateToOfflineMode, modifier = Modifier.fillMaxWidth()) {
            Text("Offline Mode Info")
        }
    }
}
