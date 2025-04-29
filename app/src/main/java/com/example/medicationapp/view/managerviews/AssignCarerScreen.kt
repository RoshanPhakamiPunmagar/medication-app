package com.example.medicationapp.view.managerviews

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medicationapp.database.AppDatabase
import com.example.medicationapp.model.Client
import com.example.medicationapp.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AssignCarerScreen() {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val clientDao = db.clientDao()
    val userDao = db.userDao()

    var clients by remember { mutableStateOf<List<Client>>(emptyList()) }
    var carers by remember { mutableStateOf<List<User>>(emptyList()) }
    var selectedClient by remember { mutableStateOf<Client?>(null) }
    var selectedCarer by remember { mutableStateOf<User?>(null) }
    var message by remember { mutableStateOf("") }

    // load once
    LaunchedEffect(Unit) {
        clients = clientDao.getAllClients()
        carers = userDao.getUsersByRole(roleId = 2)  // 2 == Carer
    }

    val ioScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Assign Carer", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        // Select Client
        Text("Select Client")
        Spacer(Modifier.height(8.dp))
        clients.forEach { client ->
            Button(
                onClick = { selectedClient = client },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Text(client.name)
            }
        }

        Spacer(Modifier.height(16.dp))

        // Select Carer
        Text("Select Carer")
        Spacer(Modifier.height(8.dp))
        carers.forEach { carer ->
            Button(
                onClick = { selectedCarer = carer },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Text(carer.name)
            }
        }

        Spacer(Modifier.height(16.dp))

        // Assign Carer button
        if (selectedClient != null && selectedCarer != null) {
            Button(
                onClick = {
                    ioScope.launch {
                        // Update the entity
                        selectedClient!!.carerId = selectedCarer!!.userId.toLong()
                        clientDao.updateClient(selectedClient!!)
                        message = "Assigned ${selectedCarer!!.name} to ${selectedClient!!.name}"
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Assign Carer")
            }
        } else {
            Text("Please select a client and a carer", color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(16.dp))

        // Feedback message after assignment
        if (message.isNotEmpty()) {
            Text(message, style = MaterialTheme.typography.bodyLarge)
        }

        // Option to Remove Carer
        Spacer(Modifier.height(16.dp))
        selectedClient?.let { client ->
            selectedCarer?.let {
                Button(
                    onClick = {
                        ioScope.launch {
                            // Remove carer by setting carerId to null
                            client.carerId = null
                            clientDao.updateClient(client)
                            message = "Removed carer from ${client.name}"
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Text("Remove Carer")
                }
            }
        }
    }
}

