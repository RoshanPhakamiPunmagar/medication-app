package com.example.medicationapp.view.managerviews

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import com.example.medicationapp.model.Client
import com.example.medicationapp.model.ClientMedication
import com.example.medicationapp.viewmodel.ClientViewModel
import android.app.TimePickerDialog
import android.util.Log
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medicationapp.model.AiAnalysisResponse
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
 * Composable screen that displays a paginated list of clients.
 *
 * Features:
 * - Fetches clients data with pagination via ClientViewModel.
 * - Shows a loading indicator while clients list is empty.
 * - Displays clients in a scrollable LazyColumn using ClientItem composable.
 * - Provides pagination controls ("Previous" and "Load More") to navigate through pages.
 * - Each client item displays basic information: name, date of birth, and contact phone.
 * - Uses Material3 components for styling and layout.
 */



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ClientListScreen(clientViewModel: ClientViewModel = viewModel()) {
    val clients by clientViewModel.clientsLiveData.observeAsState(emptyList())
    val currentPage by clientViewModel.currentPage.observeAsState(0)
    val totalPages by clientViewModel.totalPages.observeAsState(1)


    var showMedicalDetails by remember { mutableStateOf(false) }
    var showMedicalAiDetails by remember { mutableStateOf(false) }


    // Trigger the initial data load
    LaunchedEffect(Unit) {
        clientViewModel.getClientsPaged(page = 0, size = 5)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp)) {

        Text("Clients", style = MaterialTheme.typography.headlineMedium)

        if (clients.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 24.dp))
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(clients) { client ->
                    ClientItem(client = client)
                }
             //Pagination controls
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (currentPage > 0) {
                            Button(
                                onClick = {
                                    clientViewModel.getClientsPaged(currentPage - 1, 5)
                                    clientViewModel.setCurrentPage(currentPage - 1)
                                }
                            ) {
                                Text("Previous")
                            }
                        }

                        if (currentPage < totalPages - 1) {
                            Button(
                                onClick = {
                                    clientViewModel.getClientsPaged(currentPage + 1, 5)
                                    clientViewModel.setCurrentPage(currentPage + 1)
                                }
                            ) {
                                Text("Load More")
                            }
                        }
                    }
                }

                }
            }
        }
    }

@Composable
fun ClientItem(client: Client) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Name: ${client.name}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "DOB: ${client.dob}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Phone: ${client.contact}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}


@Composable
private fun MedicationCard(
    medication: ClientMedication,
    onClick: () -> Unit
) {
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
            Text("Dosage: ${medication.dosage}", style = MaterialTheme.typography.bodyMedium)
            Text("From: ${medication.startDate}", style = MaterialTheme.typography.bodySmall)
            Text("To: ${medication.endDate}", style = MaterialTheme.typography.bodySmall)
            Text("Times: ${medication.scheduledTimes.joinToString()}", style = MaterialTheme.typography.bodySmall)
            if (medication.isPaused) {
                Text("Status: Paused", color = MaterialTheme.colorScheme.error)
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
fun AiAnalysisCard(response: AiAnalysisResponse) {


    val medicationViewModel: MedicationLogViewModel = viewModel()
    val error = medicationViewModel.error
    val isLoading = medicationViewModel.isLoading
    if (isLoading) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
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
                    color = Color(0xFF6200EE) // Purple
                )
            }

            // Risks Section
            if (response.risks.isNotEmpty()) {
                AnalysisSection(
                    title = "Potential Risks",
                    content = response.risks,
                    icon = Icons.Default.Warning,
                    color = Color(0xFFD32F2F) // Red
                )
            }

            // Recommendations Section
            if (response.recommendations.isNotEmpty()) {
                AnalysisSection(
                    title = "Recommendations",
                    content = response.recommendations,
                    icon = Icons.Default.Lightbulb,
                    color = Color(0xFF388E3C) // Green
                )
            }
        }
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