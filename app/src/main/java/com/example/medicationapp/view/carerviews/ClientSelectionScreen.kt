package com.example.medicationapp.view.carerviews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun ActualTimeInput(
    actualTime: LocalTime?,
    onTimeChange: (LocalTime?) -> Unit
) {
    var timeText by remember { mutableStateOf(TextFieldValue(actualTime?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: "")) }

    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    fun parseTimeInput(input: String): LocalTime? {
        return try {
            LocalTime.parse(input, formatter)
        } catch (e: Exception) {
            null
        }
    }

    TextField(
        value = timeText,
        onValueChange = {
            timeText = it
            val parsedTime = parseTimeInput(it.text)
            onTimeChange(parsedTime)
        },
        label = { Text("Enter Actual Time (HH:mm)") },
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientSelectionScreen(
    clientController: ClientController,
    navController: NavController,
    carerId : Long
) {
    val coroutineScope = rememberCoroutineScope()
    var clients by remember { mutableStateOf<List<Client>>(emptyList()) }
    var selectedClient by remember { mutableStateOf<Client?>(null) }
    var medications by remember { mutableStateOf<List<Pair<ClientMedication, String>>>(emptyList()) }
    var selectedMedication by remember { mutableStateOf<Pair<ClientMedication, String>?>(null) }

    var actualTime by remember { mutableStateOf<LocalTime?>(null) }
    var status by remember { mutableStateOf(MedicationLog.Status.Given) }
    var notes by remember { mutableStateOf("") }

    LaunchedEffect(carerId) {
        clients = clientController.getClientsForCarer(carerId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Select Client") })
        }
    ) { innerPadding ->

        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
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

                        Text(
                            "Scheduled Time: ${
                                clientMedication.scheduledTimes.joinToString(", ") {
                                    it.format(DateTimeFormatter.ofPattern("HH:mm"))
                                }
                            }"
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        ActualTimeInput(actualTime = actualTime, onTimeChange = {
                            actualTime = it
                        })

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
                            actualTime = LocalTime.now()

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
                                //navController.navigate("adherence_screen/${clientMedication.clientMedicationId}/${userId}")
                            }
                        }

                        MedicationStatusButton("Skipped") {
                            status = MedicationLog.Status.Skipped
                            actualTime = null

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
                                //navController.navigate("adherence_screen/${clientMedication.clientMedicationId}/${userId}")
                            }
                        }

                        MedicationStatusButton("Missed") {
                            status = MedicationLog.Status.Missed
                            actualTime = null

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
                                //navController.navigate("adherence_screen/${clientMedication.clientMedicationId}/${userId}")
                            }
                        }

                        MedicationStatusButton("Late") {
                            status = MedicationLog.Status.Late
                            actualTime = LocalTime.now()

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
                                //navController.navigate("adherence_screen/${clientMedication.clientMedicationId}/${userId}")
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
    }}

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

suspend fun logMedication(
    clientMedication: ClientMedication,
    status: MedicationLog.Status,
    clientId: Long?,
    scheduledTime: LocalTime,
    actualTime: LocalTime?,
    notes: String,
    clientController: ClientController
) {
    if (clientId == null) {
        println("Error: clientId is null.")
        return
    }

    val medicationLog = MedicationLog(
        clientMedicationId = clientMedication.clientMedicationId,
        carerId = clientId,
        scheduledTime = listOf(scheduledTime),
        actualTime = actualTime,
        status = status,
        notes = notes
    )

    clientController.logMedication(medicationLog)
}
