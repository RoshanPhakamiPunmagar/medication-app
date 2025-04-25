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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientSelectionScreen(
    clientController: ClientController,
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()
    var clients by remember { mutableStateOf<List<Client>>(emptyList()) }
    var selectedClient by remember { mutableStateOf<Client?>(null) }

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
            Text(
                "Tap on a client to start your shift",
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
                                selectedClient = client
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(client.name, style = MaterialTheme.typography.titleMedium)
                            Text("Client ID: ${client.clientId}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }


        selectedClient?.let { client ->
            AlertDialog(
                onDismissRequest = { selectedClient = null },
                title = { Text("Start Shift") },
                text = { Text("Are you sure you want to start your shift with ${client.name}?") },
                confirmButton = {
                    TextButton(onClick = {
                        selectedClient = null
                        navController.navigate("carer_dashboard/${client.clientId}")
                    }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { selectedClient = null }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
