package com.example.medicationapp.view.carerviews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medicationapp.controller.ClientController
import com.example.medicationapp.model.Client
import com.example.medicationapp.model.ClientMedication
import com.example.medicationapp.model.MedicationLog
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Time Input Handler for TextField with validation
@Composable
fun ActualTimeInput(
    actualTime: LocalDateTime?,
    onTimeChange: (LocalDateTime?) -> Unit
) {
    var timeText by remember { mutableStateOf(TextFieldValue()) }

    // Format for time input, you can change the pattern as needed
    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    // This method ensures the input is formatted correctly
    fun parseTimeInput(input: String): LocalDateTime? {
        return try {
            // Attempt to parse the input as LocalDateTime
            LocalDateTime.now().withHour(input.substring(0..1).toInt()).withMinute(input.substring(3..4).toInt())
        } catch (e: Exception) {
            // If parsing fails, return null
            null
        }
    }

    TextField(
        value = timeText,
        onValueChange = {
            timeText = it
            val time = parseTimeInput(it.text)
            onTimeChange(time) // Update actual time if parsing is successful
        },
        label = { Text("Enter Actual Time") },
        modifier = Modifier.fillMaxWidth()
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientSelectionScreen(
    clientId: Long?,
    clientController: ClientController,
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()
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
            // Client selection UI
            if (selectedClient == null) {
                Text(
                    "Tap on a client to view medications",
                    style = MaterialTheme.typography.bodyMedium
                )

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
                                        medications =
                                            clientController.getMedicationsForClient(client.clientId)
                                    }
                                },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
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
                // Medication selection UI
                Text(
                    "Medications for ${selectedClient?.name}:",
                    style = MaterialTheme.typography.titleMedium
                )

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
                                Text(
                                    "Dosage: ${clientMedication.dosage}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    "Frequency: ${clientMedication.frequency}",
                                    style = MaterialTheme.typography.bodySmall
                                )
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

        selectedMedication?.let { (clientMedication, medicationName) ->
            AlertDialog(
                onDismissRequest = { selectedMedication = null },
                title = { Text("Log Medication") },
                text = {
                    Column {
                        Text("Mark medication: $medicationName")
                        Spacer(modifier = Modifier.height(8.dp))

                        // Display Scheduled Time (from manager)
                        Text("Scheduled Time: ${clientMedication.scheduledTimes.joinToString(", ") { it.toLocalTime().format(DateTimeFormatter.ofPattern("")) }}")


                        Spacer(modifier = Modifier.height(8.dp))

                        // Actual Time Input (current time when carer administers the medication)
                        ActualTimeInput(actualTime = actualTime, onTimeChange = {
                            actualTime = it // Update actual time whenever the user changes it
                        })

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
                        // Buttons to log medication status
                        MedicationStatusButton("Given") {
                            status = MedicationLog.Status.Given
                            actualTime =
                                LocalDateTime.now() // Set actual time to current time when medication is given

                            coroutineScope.launch {
                                logMedication(
                                    clientMedication = clientMedication,
                                    status = status,
                                    clientId = selectedClient?.clientId,
                                    scheduledTime = clientMedication.scheduledTimes.first(),
                                    actualTime = actualTime,
                                    notes = notes,
                                    clientController = clientController
                                )
                            }
                        }

                        MedicationStatusButton("Skipped") {
                            status = MedicationLog.Status.Skipped
                            actualTime = null // No actual time for skipped medication
                            coroutineScope.launch {
                                logMedication(
                                    clientMedication = clientMedication,
                                    status = status,
                                    clientId = selectedClient?.clientId,
                                    scheduledTime = clientMedication.scheduledTimes.first(),
                                    actualTime = actualTime,
                                    notes = notes,
                                    clientController = clientController
                                )
                            }
                        }

                        MedicationStatusButton("Missed") {
                            status = MedicationLog.Status.Missed
                            actualTime = null // No actual time for missed medication
                            coroutineScope.launch {
                                logMedication(
                                    clientMedication = clientMedication,
                                    status = status,
                                    clientId = selectedClient?.clientId,
                                    scheduledTime = clientMedication.scheduledTimes.first(),
                                    actualTime = actualTime,
                                    notes = notes,
                                    clientController = clientController
                                )
                            }
                        }

                        MedicationStatusButton("Late") {
                            status = MedicationLog.Status.Late
                            actualTime =
                                LocalDateTime.now() // Set actual time to current time when medication is given late
                            coroutineScope.launch {
                                logMedication(
                                    clientMedication = clientMedication,
                                    status = status,
                                    clientId = selectedClient?.clientId,
                                    scheduledTime = clientMedication.scheduledTimes.first(),
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

// Function to log medication entry
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
        println("Error: clientId is null.")
        return
    }

    val medicationLog = MedicationLog(
        clientMedicationId = clientMedication.clientMedicationId,
        carerId = clientId, // Use the passed clientId
        scheduledTime = scheduledTime, // Use the user-provided scheduled time
        actualTime = actualTime, // Use the user-provided actual time
        status = status, // Use the selected status
        notes = notes // Include notes from the form
    )

    clientController.logMedication(medicationLog)
}
