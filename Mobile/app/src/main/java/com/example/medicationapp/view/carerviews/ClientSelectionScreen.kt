package com.example.medicationapp.view.carerviews

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.medicationapp.model.Client
import com.example.medicationapp.model.ClientMedication
import com.example.medicationapp.model.ClientMedsDescriptions
import com.example.medicationapp.model.MedicationLog
import com.example.medicationapp.model.repository.ClientRepository
import kotlinx.coroutines.launch
import java.time.LocalTime
import com.example.medicationapp.viewmodel.MedicationDetailsViewModel




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientSelectionScreen(
    clientRepository: ClientRepository,
    navController: NavController,
    carerId: Long,
    clientMedsDetailsViewModel: MedicationDetailsViewModel = viewModel(),
) {
    // Creates a coroutine scope tied to the Composable's lifecycle.
// Used to launch suspend functions like fetching data or logging actions.
    val coroutineScope = rememberCoroutineScope()

// Stores the list of clients assigned to the carer.
// Initially an empty list, it will be filled after fetching from the repository.
    var clients by remember { mutableStateOf<List<Client>>(emptyList()) }

// Keeps track of the currently selected client from the list.
    var selectedClient by remember { mutableStateOf<Client?>(null) }

// Stores the list of medications assigned to the selected client.
// Each item is a Pair: (ClientMedication, MedicationName)
    var medications by remember { mutableStateOf<List<Pair<ClientMedication, String>>>(emptyList()) }

// Keeps track of the currently selected medication (used for logging).
    var selectedMedication by remember { mutableStateOf<Pair<ClientMedication, String>?>(null) }

// Holds all medication names for the selected client (possibly for AI or summaries).
    var allMedications by remember { mutableStateOf<List<String>>(emptyList()) }

// Stores medication names again — possibly used specifically for the AI detail screen.
    var meds by remember { mutableStateOf<List<String>>(emptyList()) }

// Holds the actual time the medication was taken (if applicable).
    var actualTime by remember { mutableStateOf<LocalTime?>(null) }

// Represents the status of the medication administration (e.g., Given, Missed).
// Defaults to "Given".
    var status by remember { mutableStateOf(MedicationLog.Status.Given) }

// A string for optional notes the carer can enter while logging medication.
    var notes by remember { mutableStateOf("") }

// Whether to show the AI-powered "More Details" section for medications.
    var showDetails by remember { mutableStateOf(false) }

// This effect runs only once when `carerId` changes (usually at screen load).
// It fetches and populates the `clients` list with all clients assigned to this carer.
    LaunchedEffect(carerId) {
        clients = clientRepository.getClientsForCarer(carerId)
    }


    // Main container providing a basic app layout structure (top bar, content, etc.)
    Scaffold(
        // Top app bar with centered title "Client List"
        topBar = { CenterAlignedTopAppBar(title = { Text("Client List") }) }
    ) { innerPadding ->

        // Scroll state for the entire column content
        val scrollState = rememberScrollState()

        // Vertically scrollable column that fills the screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(innerPadding) // Account for top bar height
                .padding(16.dp),       // Inner content padding
            verticalArrangement = Arrangement.spacedBy(12.dp) // Space between each item
        ) {

            // If no client is selected yet, show the list of available clients
            if (selectedClient == null) {
                Text(
                    "Tap on a client to view medications",
                    style = MaterialTheme.typography.bodyMedium
                )

                // If the list is empty, show a message
                if (clients.isEmpty()) {
                    Text("No clients available.")
                } else {
                    // Otherwise, show each client as a card
                    clients.forEach { client ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    coroutineScope.launch {
                                        // On click: update selected client and fetch medications
                                        selectedClient = client
                                        medications =
                                            clientRepository.getMedicationsForClient(client.clientId)
                                        meds =
                                            clientRepository.getMedicationsOfClient(client.clientId)
                                    }
                                },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text(client.name, style = MaterialTheme.typography.titleMedium)
                                Text(
                                    "Client ID: ${client.clientId}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            } else {
                // If a client is selected, show their medications
                Text(
                    "Medications for ${selectedClient?.name}:",
                    style = MaterialTheme.typography.titleMedium
                )

                if (medications.isEmpty()) {
                    Text("No medications assigned.")
                } else {
                    // Show each medication as a card
                    medications.forEach { (clientMedication, medicationName) ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    // When a medication is clicked, open the log dialog
                                    selectedMedication = clientMedication to medicationName
                                },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text(medicationName, style = MaterialTheme.typography.titleMedium)
                                Text(
                                    "Dosage: ${clientMedication.dosage}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Button to go back to the client list
                Button(onClick = {
                    showDetails = false
                    selectedClient = null
                }) {
                    Text("Back to Client List")
                }

                // Button to toggle AI-powered medication info
                Button(onClick = {
                    showDetails = !showDetails
                    if (showDetails) clientMedsDetailsViewModel.fetchMedicationDetails(meds)
                }) {
                    Text("More AI Details")
                }

                // Conditionally show the AI medication details
                if (showDetails) {
                    MoreDetails(viewModel = clientMedsDetailsViewModel)
                }
            }
        }
    }


    // If a medication is selected, show the logging dialog
    selectedMedication?.let { (clientMedication, medicationName) ->

        // Create a coroutine scope for logging operations inside dialog
        val coroutineScope = rememberCoroutineScope()

        // A dialog to log medication intake
        AlertDialog(
            onDismissRequest = {
                selectedMedication = null
            },  // Dismiss dialog if user taps outside or presses back
            title = { Text("Log Medication") },                // Dialog title

            text = {
                Column {
                    Text("Mark medication: $medicationName")   // Shows medication name
                    Spacer(modifier = Modifier.height(8.dp))

                    // Show scheduled time(s) for medication
                    Text(
                        "Scheduled Time: ${
                        clientMedication.scheduledTimes.joinToString(", ") { it.toString() }
                    }")

                    Spacer(modifier = Modifier.height(8.dp))

                    // Input field for notes (optional comments)
                    TextField(
                        value = notes,
                        onValueChange = { notes = it },        // Update notes state on input
                        label = { Text("Notes") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },

            // The confirm button area contains four buttons: one for each medication status
            confirmButton = {
                Column {
                    // Log as GIVEN
                    MedicationStatusButton("Given") {
                        status = MedicationLog.Status.Given
                        actualTime = LocalTime.now()  // Time is recorded as now

                        coroutineScope.launch {
                            logMedication(
                                clientMedication = clientMedication,
                                status = status,
                                clientId = selectedClient?.clientId, // Carer's selected client
                                scheduledTime = clientMedication.scheduledTimes.first(), // Assumes one scheduled time
                                actualTime = actualTime,
                                notes = notes,
                                clientRepository = clientRepository
                            )
                            selectedMedication = null  // Close dialog after logging
                        }
                    }

                    // Log as SKIPPED
                    MedicationStatusButton("Skipped") {
                        status = MedicationLog.Status.Skipped
                        actualTime = null  // No actual time recorded for skipped

                        coroutineScope.launch {
                            logMedication(
                                clientMedication = clientMedication,
                                status = status,
                                clientId = selectedClient?.clientId,
                                scheduledTime = clientMedication.scheduledTimes.first(),
                                actualTime = actualTime,
                                notes = notes,
                                clientRepository = clientRepository
                            )
                            selectedMedication = null
                        }
                    }

                    // Log as MISSED
                    MedicationStatusButton("Missed") {
                        status = MedicationLog.Status.Missed
                        actualTime = null  // No time for missed either

                        coroutineScope.launch {
                            logMedication(
                                clientMedication = clientMedication,
                                status = status,
                                clientId = selectedClient?.clientId,
                                scheduledTime = clientMedication.scheduledTimes.first(),
                                actualTime = actualTime,
                                notes = notes,
                                clientRepository = clientRepository
                            )
                            selectedMedication = null
                        }
                    }

                    // Log as LATE
                    MedicationStatusButton("Late") {
                        status = MedicationLog.Status.Late
                        actualTime = LocalTime.now()  // Time of late administration

                        coroutineScope.launch {
                            logMedication(
                                clientMedication = clientMedication,
                                status = status,
                                clientId = selectedClient?.clientId,
                                scheduledTime = clientMedication.scheduledTimes.first(),
                                actualTime = actualTime,
                                notes = notes,
                                clientRepository = clientRepository
                            )
                            selectedMedication = null
                        }
                    }
                }
            },

            // Dismiss/cancel button
            dismissButton = {
                TextButton(onClick = { selectedMedication = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}
@Composable
fun MedicationStatusButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()         // Makes the button take up the full width
            .padding(vertical = 4.dp), // Adds vertical spacing around the button
        onClick = onClick          // Executes the action passed when button is clicked
    ) {
        Text(text)                 // Displays the given text on the button (e.g., "Given", "Missed")
    }
}

suspend fun logMedication(
    clientMedication: ClientMedication,       // Medication instance (from selected client)
    status: MedicationLog.Status,             // Status to be logged (Given, Skipped, etc.)
    clientId: Long?,                          // Client's ID (must not be null)
    scheduledTime: LocalTime,                 // Scheduled time for medication
    actualTime: LocalTime?,                   // Actual time it was taken (nullable for Skipped/Missed)
    notes: String,                            // Optional notes
    clientRepository: ClientRepository        // Repository to save data to database
) {
    // Ensure clientId is valid before proceeding
    if (clientId == null) {
        Log.e("LogMedication", "clientId is null.")
        return
    }

    // Create a MedicationLog object from the provided parameters
    val medicationLog = MedicationLog(
        clientMedicationId = clientMedication.clientMedicationId,  // Link to client-medication relation
        carerId = clientId,                                        // Carer performing the log
        scheduledTime = listOf(scheduledTime),                     // Log expects a list (wrap single time)
        actualTime = actualTime,                                   // Can be null for Skipped/Missed
        status = status,                                           // The selected status
        notes = notes                                              // Optional carer notes
    )

    // Save the log to the database via repository
    clientRepository.logMedication(medicationLog)
}
