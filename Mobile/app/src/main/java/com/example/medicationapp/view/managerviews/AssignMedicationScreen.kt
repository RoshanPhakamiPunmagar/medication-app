package com.example.medicationapp.view.managerviews

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.medicationapp.view.alarm.AlarmScheduler
import com.example.medicationapp.model.Client
import com.example.medicationapp.model.ClientMedication
import com.example.medicationapp.model.Medication
import com.example.medicationapp.view.TimeWheelPickerDialog
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import com.example.medicationapp.model.repository.ClientRepository
import com.example.medicationapp.repository.MedicationRepository


@SuppressLint("ScheduleExactAlarm")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignMedicationScreen(
    clientRepository: ClientRepository,
    medicationRepository: MedicationRepository
)
 {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var clients by remember { mutableStateOf<List<Client>>(emptyList()) }
    var medications by remember { mutableStateOf<List<Medication>>(emptyList()) }

    var selectedClient by remember { mutableStateOf<Client?>(null) }
    var selectedMedication by remember { mutableStateOf<Medication?>(null) }

    var dosage by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var scheduledTimes by remember { mutableStateOf<List<LocalTime>>(emptyList()) }

    var successMessage by remember { mutableStateOf<String?>(null) }
    var schedules by remember { mutableStateOf<List<ClientMedication>>(emptyList()) }

    var showTimePicker by remember { mutableStateOf(false) }
    var timeInput by remember { mutableStateOf("") }



    var clientMedication by remember { mutableStateOf<ClientMedication?>(null) }

    val scheduler = AlarmScheduler(context)

    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val datePickerStateStart = rememberDatePickerState()
    val datePickerStateEnd = rememberDatePickerState()
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        clients = clientRepository.getAllClients()
        medications = medicationRepository.getAllMedications()

    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    )
    {
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
            onDismissRequest = { clientExpanded = false },
            modifier = Modifier.heightIn(max = 300.dp)
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


        // Medication Dropdown
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
            onDismissRequest = { medicationExpanded = false },
            modifier = Modifier.heightIn(max = 300.dp)
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


        //Inputs
        TextField(
            value = dosage,
            onValueChange = { dosage = it },
            label = { Text("Dosage") },
            modifier = Modifier.fillMaxWidth()
        )

        // Start Date Picker Trigger
        Button(
            onClick = { showStartDatePicker = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (startDate.isBlank()) "Select Start Date" else "Start Date: $startDate")
        }

        // End Date Picker Trigger
        Button(
            onClick = { showEndDatePicker = true },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text(if (endDate.isBlank()) "Select End Date" else "End Date: $endDate")
        }

        //Scheduled Times
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
                                clientMedication    = medicationRepository.assignMedicationToClient(
                                    clientId = clientId,
                                    medicationId = medicationId.toLong(),
                                    dosage = dosage,
                                    startDate = parsedStart,
                                    endDate = parsedEnd,
                                    scheduledTimes = timeFormated
                                )
                                Log.d("AlarmCheck", "clientMedication is ${clientMedication?.clientMedicationId}")
                                clientMedication?.let(scheduler::setUpAlarmDateRange)


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
                    dosage.isNotBlank() &&
                    startDate.isNotBlank() && endDate.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        )

        {
            Text("Assign Medication")
        }

        Button(
            onClick = {
                selectedClient = null
                selectedMedication = null
                dosage = ""
                startDate = ""
                endDate = ""
                scheduledTimes = emptyList()
                successMessage = null
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Clear All", color = MaterialTheme.colorScheme.onError)
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
                        Text("Start Date: ${schedule.startDate}")
                        Text("End Date: ${schedule.endDate}")
                    }
                }
            }
        }
    }

    // --- Use TimeWheelPickerDialog instead of text input ---
    if (showTimePicker) {
        TimeWheelPickerDialog(
            onDismiss = { showTimePicker = false },
            onTimeSelected = { hour, minute ->
                val time = LocalTime.of(hour, minute)
                scheduledTimes = scheduledTimes + time
                showTimePicker = false
            }
        )
    }

    // Start DatePicker Dialog
    if (showStartDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showStartDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val millis = datePickerStateStart.selectedDateMillis
                    if (millis != null) {
                        val date = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
                        startDate = date.format(dateFormatter)
                    }
                    showStartDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showStartDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerStateStart)
        }
    }

    // End DatePicker Dialog
    if (showEndDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showEndDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val millis = datePickerStateEnd.selectedDateMillis
                    if (millis != null) {
                        val date = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
                        endDate = date.format(dateFormatter)
                    }
                    showEndDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEndDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerStateEnd)
        }
    }

}
