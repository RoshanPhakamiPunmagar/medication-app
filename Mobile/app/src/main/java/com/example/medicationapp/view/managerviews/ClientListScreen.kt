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
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import com.example.medicationapp.model.AiAnalysisResponse
import com.example.medicationapp.model.dto.AdherenceLogDTO
import com.example.medicationapp.viewmodel.MedicationLogViewModel
import kotlin.collections.forEach
import com.example.medicationapp.model.AiReport

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



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientListScreen(clientViewModel: ClientViewModel = viewModel()) {
    val clients by clientViewModel.clientsLiveData.observeAsState(emptyList())
    val currentPage by clientViewModel.currentPage.observeAsState(0)
    val totalPages by clientViewModel.totalPages.observeAsState(1)


    var selectedClient by remember { mutableStateOf<Client?>(null) }

    var showReport by remember { mutableStateOf(false) }

    val viewModel: MedicationLogViewModel = viewModel()
    val aiReport by viewModel.aiReport.collectAsState()

    // Trigger the initial data load
    LaunchedEffect(Unit) {
        clientViewModel.getClientsPaged(page = 0, size = 5)
    }

    LaunchedEffect(selectedClient) {
        selectedClient?.let { viewModel.fetchAiReport(it.clientId) }
    }


   Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)

    ) {
        if (selectedClient == null) {
            Text("Clients", style = MaterialTheme.typography.headlineMedium)


            if (clients.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 24.dp))
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(clients) { client ->
                        ClientItem(
                            client = client,
                            onClick = {
                                selectedClient = client
                                showReport = true
                            }

                        )
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
        else if (showReport && selectedClient != null) {
                ClientReportItem(selectedClient!!, aiReport, onClick = {
                    showReport = false
                    selectedClient = null
                })
        }
    }
    }


@Composable
fun ClientItem(client: Client, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
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
fun ClientReportItem(
    client: Client,
    aiReport: AiReport?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Header Section
        Text(
            text = "Client Report",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Client Information Section
        Text(
            text = "Client Details",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Divider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        InfoRow(label = "Name", value = client.name)
        Spacer(modifier = Modifier.height(12.dp))
        InfoRow(label = "Date of Birth", value = client.dob)
        Spacer(modifier = Modifier.height(12.dp))
        InfoRow(label = "Contact", value = client.contact)

        Spacer(modifier = Modifier.height(32.dp))

        // Analysis Results Section
        Text(
            text = "Analysis Results",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Divider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        // Patterns Section
        Column(modifier = Modifier.padding(bottom = 24.dp)) {
            Text(
                text = "Patterns",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = aiReport?.patterns ?: "Analyzing patterns...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Alerts Section
        Column(modifier = Modifier.padding(bottom = 32.dp)) {
            Text(
                text = "Alerts",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = if (aiReport?.alerts?.isNotEmpty() == true) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = aiReport?.alerts ?: "No alerts detected",
                style = MaterialTheme.typography.bodyMedium,
                color = if (aiReport?.alerts?.isNotEmpty() == true) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }

        // Back Button
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                text = "Back to Client List",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    isLoading: Boolean = false,
    isAlert: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.width(150.dp)
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = if (isAlert) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

