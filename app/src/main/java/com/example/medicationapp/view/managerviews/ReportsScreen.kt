package com.example.medicationapp.view.managerviews

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ReportsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Client Reports",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Adherence and Schedule Overview",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        // Placeholder card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Client: John Doe")
                Text("Medications Taken On Time: 90%")
                Text("Missed Doses: 2")
                Text("Last Updated: 2025-04-21")
            }
        }

        // Add more cards or a LazyColumn with dynamic data
        Text(
            text = "More detailed analytics coming soon...",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
