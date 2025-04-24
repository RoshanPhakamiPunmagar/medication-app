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
    clientId: Long?,
    clientController: ClientController,
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()
    var clients by remember { mutableStateOf<List<Client>>(emptyList()) }

    LaunchedEffect(Unit) {
        clients = clientController.getAllClients()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Select Client") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (clients.isEmpty()) {
                Text("No clients available.")
            } else {
                clients.forEach { client ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(client.name, style = MaterialTheme.typography.titleMedium)
                            Text("ID: ${client.clientId}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}
