package com.example.medicationapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ManagerDashboard(
    onNavigateToClients: () -> Unit,
    onNavigateToAssignMedication: () -> Unit,
    onNavigateToAssignCarer: () -> Unit,
    onNavigateToScanMedication: () -> Unit,
    onNavigateToReports: () -> Unit,
    onNavigateToLiveCompliance: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Manager Dashboard", style = MaterialTheme.typography.headlineMedium)

        Button(onClick = onNavigateToClients, modifier = Modifier.fillMaxWidth()) {
            Text("Manage Clients")
        }

        Button(onClick = onNavigateToAssignMedication, modifier = Modifier.fillMaxWidth()) {
            Text("Assign Medications")
        }

        Button(onClick = onNavigateToAssignCarer, modifier = Modifier.fillMaxWidth()) {
            Text("Assign Carers to Clients")
        }

        Button(onClick = onNavigateToScanMedication, modifier = Modifier.fillMaxWidth()) {
            Text("Scan & Add Medication")
        }

        Button(onClick = onNavigateToReports, modifier = Modifier.fillMaxWidth()) {
            Text("Generate Compliance Reports")
        }

        Button(onClick = onNavigateToLiveCompliance, modifier = Modifier.fillMaxWidth()) {
            Text("View Live Compliance")
        }
    }
}
