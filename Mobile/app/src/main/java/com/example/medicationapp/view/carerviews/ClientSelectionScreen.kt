package com.example.medicationapp.view.carerviews

import android.app.TimePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medicationapp.model.AiAnalysisResponse
import com.example.medicationapp.model.ClientMedication
import com.example.medicationapp.model.ClientWithMedicationsDTO
import com.example.medicationapp.model.dto.AdherenceLogDTO
import com.example.medicationapp.model.dto.ClientMedicationDTO
import com.example.medicationapp.model.dto.MedicationLogDTO
import com.example.medicationapp.viewmodel.ClientMedicationViewModel
import com.example.medicationapp.viewmodel.MedicationDetailsViewModel
import com.example.medicationapp.viewmodel.MedicationLogViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.collections.forEach
import androidx.compose.foundation.lazy.items

/**
 * Composable screen that displays a list of clients for a given carer, allowing selection to view their medications.
 * - Shows a scrollable list of clients with basic info.
 * - On client selection, displays the medications assigned to that client.
 * - Provides buttons to navigate back to client list and to toggle showing additional AI-generated medication details.
 * - Uses ViewModels to observe and fetch client-medication data.
 * - Handles UI state for selected client and visibility of detailed info.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientSelectionScreen(
    carerId: Long,
    clientMedicationViewModel: ClientMedicationViewModel = viewModel(),
    clientMedsDetailsViewModel: MedicationDetailsViewModel = viewModel(),
    clientMedsDTOs : List<ClientMedicationDTO>?
) {

    // Observe the list of clients with their medications from the ViewModel
    val clientsWithMedications by clientMedicationViewModel.clientsWithMedications.observeAsState(emptyList())

    // Local state to track the selected client
    var selectedClient by remember { mutableStateOf<ClientWithMedicationsDTO?>(null) }

    // Flag to control visibility of additional (AI-generated) medication details
    var showDetails by remember { mutableStateOf(false) }
    var selectedMedication by remember { mutableStateOf<ClientMedication?>(null) }

    var showLogDialog by remember { mutableStateOf(false) }


    var showMedicalDetails by remember { mutableStateOf(false) }
    var showMedicalAiDetails by remember { mutableStateOf(false) }

    var showListOfMedications by remember { mutableStateOf(false) }


    val medicationLogViewModel : MedicationLogViewModel = viewModel()
    val adhlogs by medicationLogViewModel.adhlogs.collectAsState() // 'logs' is the delegated property
    val aiAnalysis by medicationLogViewModel.aiAnalysis.collectAsState()

    var meds  by remember { mutableStateOf<List<Long>>(emptyList()) }

    LaunchedEffect(carerId, selectedClient) {

        selectedClient?.medications?.let { medications ->
            // Extract all medicationIds into a list
            val medIds = medications.map { it.medicationId }
            meds = meds + medIds // or simply meds = medIds if you want to replace fully
        }
        selectedClient?.let { medicationLogViewModel.fetchLogs(it.clientId) }
        selectedClient?.let {medicationLogViewModel.fetchAiLogs(it.clientId)}
    }

    // Fetch data when the Composable is first launched or when the carerId changes
    LaunchedEffect(carerId) {
        clientMedicationViewModel.startFetchingClientWithMedsPeriodically(carerId)
    }

    Scaffold(
        topBar = {
            if(selectedClient !=  null ){
                CenterAlignedTopAppBar(title = { Text("${selectedClient?.clientName}") })
            }
            else{
                CenterAlignedTopAppBar(title = { Text("Client Selection Screen") })
            }
            // Display a centered top app bar with a title

        }
    ) { innerPadding ->

//    val scrollState = rememberScrollState()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (selectedClient == null) {
                item {
                    Text("Tap on a client to view medications",
                        style = MaterialTheme.typography.bodyMedium)
                }

                if (clientsWithMedications.isEmpty()) {
                    item { Text("No clients with medications available.") }
                } else {
                    items(clientsWithMedications) { client ->
                        ClientCard(client) {
                            selectedClient = client
                            showDetails = false
                        }
                    }
                }
            } else {
                item {
                    Button(
                        onClick = { showListOfMedications = !showListOfMedications },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (showListOfMedications) "Hide Medications" else "Show Medications")
                    }
                }

                if (showListOfMedications) {
                    item {
                        Text(
                            "Medications for ${selectedClient?.clientName}:",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    if (selectedClient?.medications.isNullOrEmpty()) {
                        item { Text("No medications assigned.") }
                    } else {
                        items(selectedClient?.medications ?: emptyList()) { med ->
                            MedicationCard(
                                med,
                                clientMedicationDto = clientMedsDTOs
                            ) {
                                selectedMedication = med
                                showLogDialog = true
                            }
                        }
                    }
                }



                item {
                    Button(
                        onClick = {
                            showDetails = !showDetails
                            if (showDetails) {
                                clientMedsDetailsViewModel.fetchMedicationDetails(meds)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("More AI Details")
                    }
                }

                if (showDetails) {
                    item {
                        MoreDetails(viewModel = clientMedsDetailsViewModel)
                    }
                }

                item {
                    Button(
                        onClick = {
                            selectedClient?.let { client ->
                                // Always fetch first
                                Log.d("checking","beep clcicked")
                                medicationLogViewModel.fetchAiLogs(client.clientId)
                                // Then toggle the UI
                                showMedicalAiDetails = !showMedicalAiDetails
                            } ?: run {
                                // Handle null client case
                                Log.e("ClientError", "No client selected")
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("AI Adherence Analysis")
                    }
                }

                if (showMedicalAiDetails) {
                    item {
                        // Get the same ViewModel instance

                        val aiAnalysis by medicationLogViewModel.aiAnalysis.collectAsState()
                        val isLoading = medicationLogViewModel.isLoading

                        when {
                            isLoading -> CircularProgressIndicator()
                            aiAnalysis != null -> AiAnalysisCard(viewModel = medicationLogViewModel, response = aiAnalysis!!)
                            else -> NoAdherenceMessage()
                        }
                    }
                }


                item {
                    Button(
                        onClick = {
                            showMedicalDetails = !showMedicalDetails
                            if (showMedicalDetails) {
                                selectedClient?.let {
                                    medicationLogViewModel.fetchLogs(it.clientId)
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Adherence Logs")
                    }
                }
                item {
                    Button(
                        onClick = {
                            showDetails = false
                            showLogDialog = false
                            showMedicalDetails = false
                            showMedicalAiDetails = false
                            showListOfMedications = false
                            selectedClient = null
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Back to Client List")
                    }
                }

                if (showMedicalDetails) {
                    val currLogs = adhlogs
                    item {
                        if (currLogs.isNullOrEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No adherence logs available")
                            }
                        } else {
                            MedicationAdherenceChart(currLogs)
                        }
                    }
                }
            }
        }

        if (showLogDialog) {
            MedicationLogDialog(
                carerId = carerId,
                clientMedication = selectedMedication,
                onDismiss = { showLogDialog = false },
                onSubmit = { logDto ->
                    medicationLogViewModel.postLog(logDto)
                    showLogDialog = false
                }
            )
        }
    }
}


@Composable
private fun ClientListSection(
    clientsWithMedications: List<ClientWithMedicationsDTO>,
    onClientSelected: (ClientWithMedicationsDTO) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Tap on a client to view medications", style = MaterialTheme.typography.bodyMedium)

        if (clientsWithMedications.isEmpty()) {
            Text("No clients with medications available.")
        } else {
            LazyColumn {
                items(clientsWithMedications) { client ->
                    ClientCard(
                        client = client,
                        onClick = { onClientSelected(client) }  // Pass the client here
                    )
                }
            }
        }
    }
}
@Composable
fun NoAdherenceMessage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No adherence available",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


@Composable
private fun ClientCard(
    client: ClientWithMedicationsDTO,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(client.clientName, style = MaterialTheme.typography.titleMedium)
            Text("Client ID: ${client.clientId}", style = MaterialTheme.typography.bodySmall)
        }
    }
}


@Composable
private fun MedicationCard(
    medication: ClientMedication,
    clientMedicationDto: List<ClientMedicationDTO>?,
    onClick: () -> Unit
) {


    val viewModelClientMeds: ClientMedicationViewModel = viewModel()
    val clientMedsData by viewModelClientMeds.clientsMedsLoggedUser.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (medication.isPaused) {
                MaterialTheme.colorScheme.errorContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                "Medication: ${getMedsName(clientMedicationDto, medication.medicationId)}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                "Dosage: ${medication.dosage}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text("From: ${medication.startDate}", style = MaterialTheme.typography.bodySmall)
            Text("To: ${medication.endDate}", style = MaterialTheme.typography.bodySmall)
            Text(
                "Times: ${medication.scheduledTimes.joinToString()}",
                style = MaterialTheme.typography.bodySmall
            )
            if (medication.isPaused) {
                Text("Status: Paused", color = MaterialTheme.colorScheme.error)
            }
        }

    }
}

//returns name
fun getMedsName(clientMedicationDtos: List<ClientMedicationDTO>?, medsId: Long): String {
    Log.d("debug", "Looking for medication ID: $medsId")
    if (clientMedicationDtos == null || clientMedicationDtos.isEmpty()) {
        Log.d("debug", "DTO list is null or empty.")
        return ""
    }

    clientMedicationDtos.forEach { dto ->
        Log.d("debug", "Checking dto.medicationId: ${dto.medicationId}")
        if (dto.medicationId != null && dto.medicationId == medsId) {
            Log.d("debug", "Match found: ${dto.medicationName}")
            return dto.medicationName ?: ""
        }
    }
    Log.d("debug", "No match found.")
    return ""
}
@Composable
fun MedicationLogDialog(
    carerId: Long,
    clientMedication: ClientMedication?,
    onDismiss: () -> Unit,
    onSubmit: (MedicationLogDTO) -> Unit
) {
    var selectedStatus by remember { mutableStateOf("Given") }
    var actualTime by remember { mutableStateOf<LocalTime?>(null) }
    var notes by remember { mutableStateOf("") }
    var showTimePicker by remember { mutableStateOf(false) }
    var medicationViewModel: MedicationLogViewModel = viewModel()

    val context = LocalContext.current
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Log Medication") },
        text = {
            Column {
                // STATUS DROPDOWN
                var expanded by remember { mutableStateOf(false) }
                Text("Status:")
                Box {
                    OutlinedButton(onClick = { expanded = true }) {
                        Text(selectedStatus)
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        listOf("Given", "Missed", "Late", "Skipped").forEach { status ->
                            DropdownMenuItem(
                                text = { Text(status) },
                                onClick = {
                                    selectedStatus = status
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ACTUAL TIME PICKER
                Text("Actual Time:")
                OutlinedButton(onClick = { showTimePicker = true }) {
                    Text(
                        actualTime?.format(timeFormatter) ?: "Select Time",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                if (showTimePicker) {
                    TimeWheelPickerDialog(
                        initialTime = actualTime ?: LocalTime.now(),
                        onTimeSelected = {
                            actualTime = it
                            showTimePicker = false
                        },
                        onDismiss = {
                            showTimePicker = false
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // NOTES FIELD
                Text("Notes:")
                TextField(
                    value = notes,
                    onValueChange = { notes = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (clientMedication != null && actualTime != null) {
                        onSubmit(
                            MedicationLogDTO(
                                clientMedicationId = clientMedication.clientMedicationId,
                                carerId = carerId,
                                status = selectedStatus,
                                scheduledTime = clientMedication.scheduledTimes.toString(),
                                actualTime = actualTime.toString(),
                                notes = notes
                            )
                        )
                        onDismiss()
                    }
                    else{
                        Toast.makeText(context, "Please make sure all required fields are filled in.", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Submit")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
@Composable
fun AiAnalysisCard(
    viewModel: MedicationLogViewModel = viewModel(),
    response: AiAnalysisResponse
) {
    // Observe the state from the same ViewModel instance
    val aiAnalysis by viewModel.aiAnalysis.collectAsState()
    val isLoading = viewModel.isLoading
    val error = viewModel.error

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Patterns Section
            if (response.patterns.isNotEmpty()) {
                AnalysisSection(
                    title = "Patterns Identified",
                    content = response.patterns,
                    icon = Icons.Default.Insights,
                    color = Color(0xFF6200EE)
                )
            }

            // Risks Section
            if (response.risks.isNotEmpty()) {
                AnalysisSection(
                    title = "Potential Risks",
                    content = response.risks,
                    icon = Icons.Default.Warning,
                    color = Color(0xFFD32F2F)
                )
            }

            // Recommendations Section
            if (response.recommendations.isNotEmpty()) {
                AnalysisSection(
                    title = "Recommendations",
                    content = response.recommendations,
                    icon = Icons.Default.Lightbulb,
                    color = Color(0xFF388E3C)
                )
            }
        }
    }
}
@Composable
private fun AnalysisSection(
    title: String,
    content: String,
    icon: ImageVector,
    color: Color
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color
            )
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        Text(
            text = content,
            modifier = Modifier.padding(start = 32.dp)
        )
    }
}
@Composable
fun MedicationAdherenceChart(medications: List<AdherenceLogDTO>) {
    val maxBarWidth = LocalConfiguration.current.screenWidthDp.dp * 0.8f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Medication Adherence",
                modifier = Modifier.padding(bottom = 8.dp)
            )

            medications.forEach { med ->
                val adherence = med.adherenceRate
                val color = when (med.status) {
                    "Given" -> Color(0xFF6200EE)
                    "Missed" -> Color(0xFF03A9F4)
                    else -> Color.Gray
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = med.clientMedicationName,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "${adherence.toInt()}%",
                            color = color,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .background(Color.LightGray.copy(alpha = 0.2f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(maxBarWidth * (adherence.toFloat() / 100f))
                                .background(color)
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun TimeWheelPickerDialog(
    initialTime: LocalTime,
    onTimeSelected: (LocalTime) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(true) }

    if (showDialog.value) {
        // Create the dialog and show it
        LaunchedEffect(Unit) {
            TimePickerDialog(
                context,
                { _, hour, minute ->
                    onTimeSelected(LocalTime.of(hour, minute))
                    showDialog.value = false
                },
                initialTime.hour,
                initialTime.minute,
                true // is24HourView
            ).apply {
                setOnCancelListener {
                    onDismiss()
                    showDialog.value = false
                }
            }.show()
        }
    }
}

