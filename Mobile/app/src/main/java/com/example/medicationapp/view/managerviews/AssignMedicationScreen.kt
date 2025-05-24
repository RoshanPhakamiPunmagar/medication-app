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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medicationapp.model.Client
import com.example.medicationapp.model.ClientMedication
import com.example.medicationapp.model.Medication
import com.example.medicationapp.viewmodel.MedicationViewModel
import com.example.medicationapp.viewmodel.ClientViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import androidx.compose.runtime.livedata.observeAsState
import com.example.medicationapp.model.dto.ClientMedicationDTO
import com.example.medicationapp.view.TimeWheelPickerDialog
import com.example.medicationapp.viewmodel.ClientMedicationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignMedicationScreen(
    viewModel: MedicationViewModel = viewModel(),
    clientViewModel: ClientViewModel = viewModel(),
    clientMedicationViewModel: ClientMedicationViewModel = viewModel()
) {
    val context = LocalContext.current

    val medications by viewModel.medications.observeAsState(emptyList())
    val clients by clientViewModel.clientsLiveData.observeAsState(emptyList())
    val assignStatus by clientMedicationViewModel.assignStatus.observeAsState()
    val assignMessage by clientMedicationViewModel.assignMessage.observeAsState()

    var selectedClient by remember { mutableStateOf<Client?>(null) }
    var selectedMedication by remember { mutableStateOf<Medication?>(null) }
    var dosage by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var scheduledTimes by remember { mutableStateOf<List<LocalTime>>(emptyList()) }

    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val scrollState = rememberScrollState()
    val datePickerStateStart = rememberDatePickerState()
    val datePickerStateEnd = rememberDatePickerState()
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }



    LaunchedEffect(Unit) {
        clientViewModel.getClientsPaged(page = 0, size = 1000)
        viewModel.fetchMedications()
    }

    LaunchedEffect(assignMessage) {
        assignMessage?.let {
            Toast.makeText(
                context,
                it,
                Toast.LENGTH_SHORT
            ).show()

            // Optional: Reset the form on successful assignment
            if (assignStatus == true) {
                selectedClient = null
                selectedMedication = null
                dosage = ""
                startDate = ""
                endDate = ""
                scheduledTimes = emptyList()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Assign Medication", style = MaterialTheme.typography.headlineMedium)

        // Client dropdown
        var clientExpanded by remember { mutableStateOf(false) }
        TextField(
            value = selectedClient?.name ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Select Client") },
            trailingIcon = {
                IconButton(onClick = { clientExpanded = !clientExpanded }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(clientExpanded, { clientExpanded = false }) {
            clients.forEach {
                DropdownMenuItem(
                    text = { Text(it.name) },
                    onClick = {
                        selectedClient = it
                        clientExpanded = false
                    }
                )
            }
        }

        // Medication dropdown
        var medicationExpanded by remember { mutableStateOf(false) }
        TextField(
            value = selectedMedication?.name ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Select Medication") },
            trailingIcon = {
                IconButton(onClick = { medicationExpanded = !medicationExpanded }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth()

        )
        DropdownMenu(medicationExpanded, { medicationExpanded = false }) {
            medications.forEach {
                DropdownMenuItem(
                    text = { Text(it.name) },
                    onClick = {
                        selectedMedication = it
                        medicationExpanded = false
                    }
                )
            }
        }

        // Dosage input
        TextField(
            value = dosage,
            onValueChange = { dosage = it },
            label = { Text("Dosage") },
            modifier = Modifier.fillMaxWidth()
        )

        // Start and end date pickers
        Button(onClick = { showStartDatePicker = true }, modifier = Modifier.fillMaxWidth()) {
            Text(if (startDate.isBlank()) "Select Start Date" else "Start Date: $startDate")
        }
        Button(onClick = { showEndDatePicker = true }, modifier = Modifier.fillMaxWidth()) {
            Text(if (endDate.isBlank()) "Select End Date" else "End Date: $endDate")
        }

        // Scheduled times list
        Text("Scheduled Times:")
        scheduledTimes.sorted().forEach { time ->
            Text(time.toString())
        }
        Button(onClick = { showTimePicker = true }, modifier = Modifier.fillMaxWidth()) {
            Text("Add Scheduled Time")
        }

        // Assign Medication Button
        Button(
            onClick = {
                try {
                    val parsedStart = LocalDate.parse(startDate)
                    val parsedEnd = LocalDate.parse(endDate)

                    // Define a time formatter
                    val formatter = DateTimeFormatter.ofPattern("HH:mm")


                    if (selectedClient == null || selectedMedication == null) {
                        Toast.makeText(
                            context,
                            "Select client and medication",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    val dto = ClientMedicationDTO(
                        clientId = selectedClient!!.clientId.toLong(),
                        medicationId = selectedMedication!!.medicationId.toLong(),
                        dosage = dosage,
                        startDate = parsedStart,
                        endDate = parsedEnd,
                        medicationName = selectedMedication!!.name,
                        clientName = selectedClient!!.name,
                        scheduledTimes = scheduledTimes,
                        isPaused = false
                    )

                    Log.d("AssignMedication", "Client ID: ${selectedClient!!.clientId}, Medication ID: ${selectedMedication!!.medicationId}")


                    clientMedicationViewModel.assignMedicationToClient(dto)
                }
                    catch (e: Exception) {
                        Log.e("AssignMedication", "Exception occurred", e)
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }

                },

            enabled = selectedClient != null && selectedMedication != null &&
                    dosage.isNotBlank() && startDate.isNotBlank() && endDate.isNotBlank(),
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Assign Medication")
        }

        // Clear form
        Button(
            onClick = {
                selectedClient = null
                selectedMedication = null
                dosage = ""
                startDate = ""
                endDate = ""
                scheduledTimes = emptyList()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Clear All", color = MaterialTheme.colorScheme.onError)
        }

//        // Show assignment message
//        assignMessage?.let {
//            Text(it, color = if (assignStatus == true) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error)
//        }

        Text(
            text = assignMessage ?: "",
            color = if (assignStatus == true) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
        )


        // Date Pickers
        if (showStartDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showStartDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerStateStart.selectedDateMillis?.let {
                            val date = LocalDate.ofEpochDay(it / (24 * 60 * 60 * 1000))
                            startDate = date.format(dateFormatter)
                        }
                        showStartDatePicker = false
                    }) { Text("OK") }
                },
                dismissButton = {
                    TextButton(onClick = { showStartDatePicker = false }) { Text("Cancel") }
                }
            ) {
                DatePicker(state = datePickerStateStart)
            }
        }

        if (showEndDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showEndDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerStateEnd.selectedDateMillis?.let {
                            val date = LocalDate.ofEpochDay(it / (24 * 60 * 60 * 1000))
                            endDate = date.format(dateFormatter)
                        }
                        showEndDatePicker = false
                    }) { Text("OK") }
                },
                dismissButton = {
                    TextButton(onClick = { showEndDatePicker = false }) { Text("Cancel") }
                }
            ) {
                DatePicker(state = datePickerStateEnd)
            }
        }

        // Time Picker Dialog
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
    }
}
