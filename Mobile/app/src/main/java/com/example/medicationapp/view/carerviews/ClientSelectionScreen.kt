package com.example.medicationapp.view.carerviews

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medicationapp.model.ClientWithMedicationsDTO
import com.example.medicationapp.viewmodel.ClientMedicationViewModel
import com.example.medicationapp.viewmodel.MedicationDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientSelectionScreen(
    carerId: Long,
    clientMedicationViewModel: ClientMedicationViewModel = viewModel(),
    clientMedsDetailsViewModel: MedicationDetailsViewModel = viewModel(),
) {
    // Observe the list of clients with their medications from the ViewModel
    val clientsWithMedications by clientMedicationViewModel.clientsWithMedications.observeAsState(emptyList())

    // Local state to track the selected client
    var selectedClient by remember { mutableStateOf<ClientWithMedicationsDTO?>(null) }

    // Flag to control visibility of additional (AI-generated) medication details
    var showDetails by remember { mutableStateOf(false) }

    // Fetch data when the Composable is first launched or when the carerId changes
    LaunchedEffect(carerId) {
        clientMedicationViewModel.fetchClientsWithMedications(carerId)
    }

    Scaffold(
        topBar = {
            // Display a centered top app bar with a title
            CenterAlignedTopAppBar(title = { Text("Client List") })
        }
    ) { innerPadding ->

        val scrollState = rememberScrollState()

        // Main column that holds all content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // If no client is selected, show the list of clients
            if (selectedClient == null) {

                Text("Tap on a client to view medications", style = MaterialTheme.typography.bodyMedium)

                if (clientsWithMedications.isEmpty()) {
                    // No clients available
                    Text("No clients with medications available.")
                } else {
                    // Show each client in a card
                    clientsWithMedications.forEach { clientWithMeds ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedClient = clientWithMeds
                                    showDetails = false
                                },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text(clientWithMeds.clientName, style = MaterialTheme.typography.titleMedium)
                                Text("Client ID: ${clientWithMeds.clientId}", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            } else {
                // A client is selected, show their medications
                Text(
                    "Medications for ${selectedClient?.clientName}:",
                    style = MaterialTheme.typography.titleMedium
                )

                // Debug log for developer insight
                Log.d("DEBUG", "Fetching for carerId = $carerId")

                if (selectedClient?.medications.isNullOrEmpty()) {
                    // Client has no medications assigned
                    Text("No medications assigned.")
                } else {
                    // List each medication in a card with details
                    selectedClient?.medications?.forEach { med ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text("Dosage: ${med.dosage}", style = MaterialTheme.typography.bodyMedium)
                                Text("From: ${med.startDate}", style = MaterialTheme.typography.bodySmall)
                                Text("To: ${med.endDate}", style = MaterialTheme.typography.bodySmall)
                                Text("Times: ${med.scheduledTimes.joinToString()}", style = MaterialTheme.typography.bodySmall)

                                // If medication is paused, show status in error color
                                if (med.isPaused) {
                                    Text("Status: Paused", color = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Button to go back to the list of clients
                Button(onClick = {
                    selectedClient = null
                    showDetails = false
                }) {
                    Text("Back to Client List")
                }

                // Button to toggle showing additional medication details
                Button(onClick = {
                    showDetails = !showDetails
                    if (showDetails) {
                        // Placeholder: fetch more details if necessary
                        clientMedsDetailsViewModel.fetchMedicationDetails(emptyList())
                    }
                }) {
                    Text("More AI Details")
                }

                // Conditionally render the MoreDetails Composable
                if (showDetails) {
                    MoreDetails(viewModel = clientMedsDetailsViewModel)
                }
            }
        }
    }
}
