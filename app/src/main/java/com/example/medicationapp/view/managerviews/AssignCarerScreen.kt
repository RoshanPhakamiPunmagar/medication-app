package com.example.medicationapp.view.managerviews

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.medicationapp.database.AppDatabase
import com.example.medicationapp.model.Client
import com.example.medicationapp.model.User
import kotlinx.coroutines.launch

@Composable
fun SectionHeader(title: String, icon: ImageVector) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null)
        Spacer(Modifier.width(8.dp))
        Text(title, style = MaterialTheme.typography.titleMedium)
    }
    Spacer(Modifier.height(8.dp))
}

@Composable
fun ConfirmDialog(title: String, text: String, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text(title) },
        text = { Text(text) }
    )
}


@Composable
fun AssignCarerScreen() {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val clientDao = db.clientDao()
    val userDao = db.userDao()

    var clients by remember { mutableStateOf(emptyList<Client>()) }
    var carers by remember { mutableStateOf(emptyList<User>()) }
    var selectedClient by remember { mutableStateOf<Client?>(null) }
    var selectedCarer by remember { mutableStateOf<User?>(null) }
    var message by remember { mutableStateOf("") }
    var showAssignConfirm by remember { mutableStateOf(false) }
    var showRemoveConfirm by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        clients = clientDao.getAllClients()
        carers = userDao.getUsersByRole(roleId = 2)
    }


    // ⬇ Add scroll state
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState) // ⬅️ Enable vertical scrolling
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("Assign Carer", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        SectionHeader("Select Client", Icons.Default.Person)
        clients.forEach { client ->
            OutlinedButton(
                onClick = { selectedClient = client },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (selectedClient == client)
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    else MaterialTheme.colorScheme.surface
                )
            ) {
                Text(client.name)
            }
        }

        Spacer(Modifier.height(24.dp))

        SectionHeader("Select Carer", Icons.Default.PersonAdd)
        carers.forEach { carer ->
            OutlinedButton(
                onClick = { selectedCarer = carer },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (selectedCarer == carer)
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    else MaterialTheme.colorScheme.surface
                )
            ) {
                Text(carer.name)
            }
        }

        Spacer(Modifier.height(24.dp))

        AnimatedVisibility(visible = selectedClient != null && selectedCarer != null) {
            Button(
                onClick = { showAssignConfirm = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Check, contentDescription = "Assign")
                Spacer(Modifier.width(8.dp))
                Text("Assign Carer")
            }
        }

        AnimatedVisibility(visible = selectedClient?.carerId != null) {
            Button(
                onClick = { showRemoveConfirm = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Remove Carer")
                Spacer(Modifier.width(8.dp))
                Text("Remove Carer")
            }
        }

        Spacer(Modifier.height(16.dp))

        if (message.isNotEmpty()) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

    // Confirmation dialogs remain unchanged...
    if (showAssignConfirm) {
        ConfirmDialog(
            title = "Confirm Assignment",
            text = "Assign ${selectedCarer?.name} to ${selectedClient?.name}?",
            onConfirm = {
                scope.launch {
                    selectedClient?.carerId = selectedCarer?.userId
                    selectedClient?.let { clientDao.updateClient(it) }
                    message = "Assigned ${selectedCarer?.name} to ${selectedClient?.name}"
                    showAssignConfirm = false
                }
            },
            onDismiss = { showAssignConfirm = false }
        )
    }

    if (showRemoveConfirm) {
        ConfirmDialog(
            title = "Confirm Removal",
            text = "Remove carer from ${selectedClient?.name}?",
            onConfirm = {
                scope.launch {
                    selectedClient?.carerId = null
                    selectedClient?.let { clientDao.updateClient(it) }
                    message = "Removed carer from ${selectedClient?.name}"
                    showRemoveConfirm = false
                }
            },
            onDismiss = { showRemoveConfirm = false }
        )
    }
}
