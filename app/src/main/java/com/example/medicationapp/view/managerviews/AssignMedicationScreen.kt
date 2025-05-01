package com.example.medicationapp.view.managerviews

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.medicationapp.controller.ClientController
import com.example.medicationapp.controller.MedicationController
import com.example.medicationapp.controller.alarm.AlarmReceiver
import com.example.medicationapp.controller.alarm.AlarmScheduler
import com.example.medicationapp.model.Client
import com.example.medicationapp.model.ClientMedication
import com.example.medicationapp.model.Medication
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)

@SuppressLint("ScheduleExactAlarm")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignMedicationScreen(
    clientController: ClientController,
    medicationController: MedicationController
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var clients by remember { mutableStateOf<List<Client>>(emptyList()) }
    var medications by remember { mutableStateOf<List<Medication>>(emptyList()) }

    var selectedClient by remember { mutableStateOf<Client?>(null) }
    var selectedMedication by remember { mutableStateOf<Medication?>(null) }

    var dosage by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var scheduledTimes by remember { mutableStateOf<List<LocalTime>>(emptyList()) }

    var successMessage by remember { mutableStateOf<String?>(null) }
    var schedules by remember { mutableStateOf<List<ClientMedication>>(emptyList()) }

    var showTimePicker by remember { mutableStateOf(false) }
    var timeInput by remember { mutableStateOf("") }



    var clientMedication by remember { mutableStateOf<ClientMedication?>(null) }

    val scheduler = AlarmScheduler(context)


    LaunchedEffect(Unit) {
        clients = clientController.getAllClients()
        medications = medicationController.getAllMedications()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Assign Medication", style = MaterialTheme.typography.headlineMedium)

        // --- Client Dropdown ---
        var clientExpanded by remember { mutableStateOf(false) }
        TextField(
            value = selectedClient?.name ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Select Client") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { clientExpanded = !clientExpanded }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            }
        )
        DropdownMenu(
            expanded = clientExpanded,
            onDismissRequest = { clientExpanded = false }
        ) {
            clients.forEach { client ->
                DropdownMenuItem(
                    text = { Text(client.name) },
                    onClick = {
                        selectedClient = client
                        clientExpanded = false
                    }
                )
            }
        }

        // --- Medication Dropdown ---
        var medicationExpanded by remember { mutableStateOf(false) }
        TextField(
            value = selectedMedication?.name ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Select Medication") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { medicationExpanded = !medicationExpanded }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            }
        )
        DropdownMenu(
            expanded = medicationExpanded,
            onDismissRequest = { medicationExpanded = false }
        ) {
            medications.forEach { med ->
                DropdownMenuItem(
                    text = { Text(med.name) },
                    onClick = {
                        selectedMedication = med
                        medicationExpanded = false
                    }
                )
            }
        }

        // --- Inputs ---
        TextField(
            value = dosage,
            onValueChange = { dosage = it },
            label = { Text("Dosage") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = frequency,
            onValueChange = { frequency = it },
            label = { Text("Frequency") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = startDate,
            onValueChange = { startDate = it },
            label = { Text("Start Date (yyyy-MM-dd)") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = endDate,
            onValueChange = { endDate = it },
            label = { Text("End Date (yyyy-MM-dd)") },
            modifier = Modifier.fillMaxWidth()
        )

        // --- Scheduled Times ---
        Text("Scheduled Times:", style = MaterialTheme.typography.titleMedium)
        if (scheduledTimes.isEmpty()) {
            Text("No times added yet.")
        } else {
            scheduledTimes.sorted().forEach { time ->
                Text(time.toString(), style = MaterialTheme.typography.bodyLarge)
            }
        }

        Button(
            onClick = { showTimePicker = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Scheduled Time")
        }

        // --- Save Button ---
        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        val parsedStart = LocalDate.parse(startDate)
                        val parsedEnd = LocalDate.parse(endDate)

                        val formatter = DateTimeFormatter.ofPattern("HH:mm")
                        val timeFormated: List<LocalTime> = scheduledTimes.mapNotNull { timeStr ->
                            try {
                                Log.e("TimeParse", "Sucess to parse $timeStr")
                                LocalTime.parse(timeStr.toString(), formatter)
                            } catch (e: Exception) {
                                Log.e("TimeParse", "Failed to parse $timeStr", e)
                                null // Skip invalid times
                            }
                        }.filterNotNull() // Remove any nulls from failed parses

                        if (timeFormated.isEmpty()) {
                            // Show error to user
                            Toast.makeText(context, "Please enter valid times", Toast.LENGTH_SHORT).show()

                        }
                        selectedClient?.clientId?.let { clientId ->
                            selectedMedication?.medicationId?.let { medicationId ->
                                medicationController.assignMedicationToClient(
                                clientMedication    = medicationController.assignMedicationToClient(
                                    clientId = clientId,
                                    medicationId = medicationId.toLong(),
                                    dosage = dosage,
                                    frequency = frequency,
                                    startDate = parsedStart,
                                    endDate = parsedEnd,
                                    scheduledTimes = scheduledTimes
                                    scheduledTimes = timeFormated
                                )
                                Log.d("AlarmCheck", "clientMedication is ${clientMedication?.clientMedicationId}")
                                clientMedication?.let(scheduler::setUpAlarm)


                                successMessage = "Medication assigned successfully!"
                            }
                        }
                    } catch (e: DateTimeParseException) {
                        successMessage = "Invalid date format. Use yyyy-MM-dd."
                    } catch (e: Exception) {
                        successMessage = "Error: ${e.message}"
                    }
                }
            },
            enabled = selectedClient != null && selectedMedication != null &&
                    dosage.isNotBlank() && frequency.isNotBlank() &&
                    startDate.isNotBlank() && endDate.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Assign Medication")
        }

        successMessage?.let {
            Text(it, color = MaterialTheme.colorScheme.primary)
        }

        // --- Existing Schedules ---
        if (schedules.isNotEmpty()) {
            Text("Current Schedules", style = MaterialTheme.typography.titleMedium)
            schedules.forEach { schedule ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Medication: ${schedule.medicationId}")
                        Text("Dosage: ${schedule.dosage}")
                        Text("Frequency: ${schedule.frequency}")
                        Text("Start Date: ${schedule.startDate}")
                        Text("End Date: ${schedule.endDate}")
                    }
                }
            }
        }
    }

    // --- Simple Time Picker Dialog ---
    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            title = { Text("Add Scheduled Time") },
            text = {
                Column {
                    TextField(
                        value = timeInput,
                        onValueChange = { timeInput = it },
                        label = { Text("Enter Time") }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        try {
                            val time = LocalTime.parse(timeInput)
                            scheduledTimes = scheduledTimes + time
                            showTimePicker = false
                            timeInput = ""
                        } catch (e: Exception) {
                        }
                    }
                ) {
                    Text("Add Time")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
