package com.example.medicationapp.view.managerviews

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.medicationapp.controller.ClientController
import com.example.medicationapp.model.Client
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ClientListScreen(clientController: ClientController) {
    val coroutineScope = rememberCoroutineScope()
    var clients by remember { mutableStateOf<List<Client>>(emptyList()) }

    // Fetch clients from the database when the screen is launched
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            clients = clientController.getAllClients()  // Call the controller to get all clients
        }
    }

    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Text("Clients", style = MaterialTheme.typography.headlineMedium)

        // Display a list of clients
        if (clients.isEmpty()) {
            Text("No clients available", style = MaterialTheme.typography.bodyMedium)
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(clients) { client ->
                    ClientItem(client = client)  // Custom composable to display each client
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
            Text(text = "Email: ${client.dob}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Phone: ${client.contactInfo}", style = MaterialTheme.typography.bodyMedium)

        }
    }
}
