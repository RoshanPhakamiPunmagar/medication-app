package com.example.medicationapp.view.managerviews

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.medicationapp.controller.ClientController
import com.example.medicationapp.controller.MedicationController
import com.example.medicationapp.model.Client
import com.example.medicationapp.model.ClientMedication
import com.example.medicationapp.model.Medication
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeParseException
import kotlin.collections.isNotEmpty


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignMedicationScreen(
    clientController: ClientController,
    medicationController: MedicationController
) {
    val coroutineScope = rememberCoroutineScope()

    var clients by remember { mutableStateOf<List<Client>>(emptyList()) }
    var medications by remember { mutableStateOf<List<Medication>>(emptyList()) }

    var selectedClient by remember { mutableStateOf<Client?>(null) }
    var selectedMedication by remember { mutableStateOf<Medication?>(null) }

    var dosage by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    var successMessage by remember { mutableStateOf<String?>(null) }
    var schedules by remember { mutableStateOf<List<ClientMedication>>(emptyList()) }

    LaunchedEffect(Unit) {
        clients = clientController.getAllClients()
        medications = medicationController.getAllMedications()
    }

    LaunchedEffect(selectedClient) {
        selectedClient?.clientId?.let { id ->
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Assign Medication", style = MaterialTheme.typography.headlineMedium)

        // Client Dropdown
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

        // Input Fields
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


        // Assign Button
        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        val parsedStart = LocalDate.parse(startDate)
                        val parsedEnd = LocalDate.parse(endDate)

                        selectedClient?.clientId?.let { clientId ->
                            selectedMedication?.medicationId?.let { medicationId ->
                                medicationController.assignMedicationToClient(
                                    clientId = clientId,
                                    medicationId = medicationId.toLong(),
                                    dosage = dosage,
                                    frequency = frequency,
                                    startDate = parsedStart,
                                    endDate = parsedEnd
                                )
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

        // Show Existing Schedules
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
}
