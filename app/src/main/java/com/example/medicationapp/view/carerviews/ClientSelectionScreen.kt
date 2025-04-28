package com.example.medicationapp.view.carerviews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medicationapp.controller.ClientController
import com.example.medicationapp.model.Client
import com.example.medicationapp.model.ClientMedication
import com.example.medicationapp.model.MedicationLog
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientSelectionScreen(
    clientId: Long?,
    clientController: ClientController,
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope() // Used for launching coroutines
    var clients by remember { mutableStateOf<List<Client>>(emptyList()) }
    var selectedClient by remember { mutableStateOf<Client?>(null) }
    var medications by remember { mutableStateOf<List<Pair<ClientMedication, String>>>(emptyList()) }
    var selectedMedication by remember { mutableStateOf<Pair<ClientMedication, String>?>(null) }

    var scheduledTime by remember { mutableStateOf(LocalDateTime.now()) }
    var actualTime by remember { mutableStateOf<LocalDateTime?>(null) }
    var status by remember { mutableStateOf(MedicationLog.Status.Given) }
    var notes by remember { mutableStateOf("") }

    // Load clients on launch
    LaunchedEffect(Unit) {
        clients = clientController.getAllClients()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Select Client") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (selectedClient == null) {
                Text("Tap on a client to view medications", style = MaterialTheme.typography.bodyMedium)

                if (clients.isEmpty()) {
                    Text("No clients available.")
                } else {
                    clients.forEach { client ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    coroutineScope.launch {
                                        selectedClient = client
                                        medications = clientController.getMedicationsForClient(client.clientId)
                                    }
                                },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(client.name, style = MaterialTheme.typography.titleMedium)
                                Text("Client ID: ${client.clientId}", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            } else {
                Text("Medications for ${selectedClient?.name}:", style = MaterialTheme.typography.titleMedium)

                if (medications.isEmpty()) {
                    Text("No medications assigned.")
                } else {
                    medications.forEach { (clientMedication, medicationName) ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedMedication = Pair(clientMedication, medicationName)
                                },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(medicationName, style = MaterialTheme.typography.titleMedium)
                                Text("Dosage: ${clientMedication.dosage}", style = MaterialTheme.typography.bodySmall)
                                Text("Frequency: ${clientMedication.frequency}", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(onClick = { selectedClient = null }) {
                    Text("Back to Client List")
                }
            }
        }

        // Medication Log Dialog
        selectedMedication?.let { (clientMedication, medicationName) ->
            AlertDialog(
                onDismissRequest = { selectedMedication = null },
                title = { Text("Log Medication") },
                text = {
                    Column {
                        Text("Mark medication: $medicationName")
                        Spacer(modifier = Modifier.height(8.dp))

                        // Scheduled Time Picker
                        Text("Scheduled Time:")
                        TextField(
                            value = scheduledTime.toString(),
                            onValueChange = { newTime ->
                                // You can add proper validation or date-time pickers for better UI
                                scheduledTime = LocalDateTime.parse(newTime)
                            },
                            label = { Text("Enter Scheduled Time") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Actual Time Picker (optional for the user to fill)
                        Text("Actual Time:")
                        TextField(
                            value = actualTime?.toString() ?: "",
                            onValueChange = { newTime ->
                                if (newTime.isNotEmpty()) {
                                    actualTime = LocalDateTime.parse(newTime)
                                } else {
                                    actualTime = null
                                }
                            },
                            label = { Text("Enter Actual Time (Optional)") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Notes field
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = notes,
                            onValueChange = { notes = it },
                            label = { Text("Notes") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Column {
                        MedicationStatusButton("Given") {
                            status = MedicationLog.Status.Given
                            coroutineScope.launch {
                                logMedication(
                                    clientMedication = clientMedication,
                                    status = status,
                                    clientId = clientId,
                                    scheduledTime = scheduledTime,
                                    actualTime = actualTime,
                                    notes = notes,
                                    clientController = clientController
                                )
                            }
                        }
                        MedicationStatusButton("Skipped") {
                            status = MedicationLog.Status.Skipped
                            coroutineScope.launch {
                                logMedication(
                                    clientMedication = clientMedication,
                                    status = status,
                                    clientId = clientId,
                                    scheduledTime = scheduledTime,
                                    actualTime = actualTime,
                                    notes = notes,
                                    clientController = clientController
                                )
                            }
                        }
                        MedicationStatusButton("Missed") {
                            status = MedicationLog.Status.Missed
                            coroutineScope.launch {
                                logMedication(
                                    clientMedication = clientMedication,
                                    status = status,
                                    clientId = clientId,
                                    scheduledTime = scheduledTime,
                                    actualTime = actualTime,
                                    notes = notes,
                                    clientController = clientController
                                )
                            }
                        }
                        MedicationStatusButton("Late") {
                            status = MedicationLog.Status.Late
                            coroutineScope.launch {
                                logMedication(
                                    clientMedication = clientMedication,
                                    status = status,
                                    clientId = clientId,
                                    scheduledTime = scheduledTime,
                                    actualTime = actualTime,
                                    notes = notes,
                                    clientController = clientController
                                )
                            }
                        }
                    }
                },
                dismissButton = {
                    TextButton(onClick = { selectedMedication = null }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

// Function to log medication entry inside coroutine scope
suspend fun logMedication(
    clientMedication: ClientMedication,
    status: MedicationLog.Status,
    clientId: Long?,
    scheduledTime: LocalDateTime,
    actualTime: LocalDateTime?,
    notes: String,
    clientController: ClientController
) {
    if (clientId == null) {
        // Handle the error if clientId is null
        println("Error: clientId is null.")
        return
    }

    // Log the medication entry inside LaunchedEffect to handle coroutine scope properly
    clientController.logMedication(
        MedicationLog(
            clientMedicationId = clientMedication.clientMedicationId,
            carerId = clientId, // Use the passed clientId
            scheduledTime = scheduledTime, // Use the user-provided scheduled time
            actualTime = actualTime, // Use the user-provided actual time
            status = status, // Use the selected status
            notes = notes // Include notes from the form
        )
    )
}

@Composable
fun MedicationStatusButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onClick
    ) {
        Text(text)
    }
}
