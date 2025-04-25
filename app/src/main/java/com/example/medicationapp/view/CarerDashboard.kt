package com.example.medicationapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CarerDashboard(
    clientId: Long?,
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

        // Display current client if selected
        if (clientId != null) {
            Text(
                text = "Currently selected client ID: $clientId",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            Text(
                text = "No client selected. Please select a client to begin shift.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(onClick = onNavigateToClientSelection, modifier = Modifier.fillMaxWidth()) {
            Text("Start Shift / Select Client")
        }

        Button(
            onClick = onNavigateToReminders,
            modifier = Modifier.fillMaxWidth(),
            enabled = clientId != null
        ) {
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
