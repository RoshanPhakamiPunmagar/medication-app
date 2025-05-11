package com.example.medicationapp.view.managerviews

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medicationapp.model.Client
import com.example.medicationapp.model.User
import com.example.medicationapp.model.repository.ClientRepository
import com.example.medicationapp.viewmodel.ClientViewModel
import com.example.medicationapp.viewmodel.UserViewModel
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
fun AssignCarerScreen(clientViewModel: ClientViewModel = viewModel(),
                      userViewModel: UserViewModel = viewModel(),) {

    // Coroutine scope for launching suspend functions (like database updates)
    val scope = rememberCoroutineScope()


    // Currently selected client from the list
    var selectedClient by remember { mutableStateOf<Client?>(null) }

    // Currently selected carer from the list
    var selectedCarer by remember { mutableStateOf<User?>(null) }

    // Message to display status (assignment/removal)
    var message by remember { mutableStateOf("") }

    // Flags to show confirmation dialogs
    var showAssignConfirm by remember { mutableStateOf(false) }
    var showRemoveConfirm by remember { mutableStateOf(false) }

    val clients by clientViewModel.clientsLiveData.observeAsState(emptyList())

    val carers by userViewModel.carersLiveData.observeAsState(emptyList()) // for LiveData

    // Enables vertical scrolling of the screen
    val scrollState = rememberScrollState()

    // Loads data once when the Composable enters the composition
    LaunchedEffect(Unit) {
        clientViewModel.getAllClients()
        userViewModel.fetchCarers()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()                    // Fill the entire screen
            .verticalScroll(scrollState)     // Make the column scrollable vertically
            .padding(24.dp),                 // Add padding around the entire column
        verticalArrangement = Arrangement.Top // Arrange children from top to bottom
    ) {
        // Title of the screen
        Text("Assign Carer", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(24.dp)) // Space below the title

        // Section header for client selection, with an icon
        SectionHeader("Select Client", Icons.Default.Person)

        // Loop through the list of clients and display each as a button
        clients.forEach { client ->
            OutlinedButton(
                onClick = { selectedClient = client }, // Select this client when clicked
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp), // Add vertical spacing between buttons
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (selectedClient == client)
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) // Highlight if selected
                    else MaterialTheme.colorScheme.surface // Default background otherwise
                )
            ) {
                Text(client.name) // Display client's name in the button
            }
        }

        Spacer(Modifier.height(24.dp)) // Space between client and carer sections

        // Section header for carer selection, with an icon
        SectionHeader("Select Carer", Icons.Default.PersonAdd)

        // Loop through the list of carers and display each as a button
        carers.forEach { carer ->
            OutlinedButton(
                onClick = { selectedCarer = carer }, // Select this carer when clicked
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (selectedCarer == carer)
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) // Highlight if selected
                    else MaterialTheme.colorScheme.surface
                )
            ) {
                Text(carer.name) // Display carer's name in the button
            }
            if (carers.isEmpty()) {
                Text("No carers found.", style = MaterialTheme.typography.bodyMedium)
            }


        }


        Spacer(Modifier.height(24.dp)) // Adds a vertical space between UI elements

// This block shows the "Assign Carer" button only when both a client and a carer are selected
        AnimatedVisibility(visible = selectedClient != null && selectedCarer != null) {
            Button(
                onClick = {
                    showAssignConfirm = true
                }, // When clicked, set the flag to show confirmation dialog
                modifier = Modifier.fillMaxWidth(), // Make the button fill the available width
                shape = RoundedCornerShape(12.dp) // Set rounded corners for the button
            ) {
                Icon(Icons.Default.Check, contentDescription = "Assign") // Display a check icon
                Spacer(Modifier.width(8.dp)) // Add space between icon and text
                Text("Assign Carer") // Button label text
            }
        }

// This block shows the "Remove Carer" button only if the selected client already has a carer assigned
        AnimatedVisibility(visible = selectedClient?.carerId != null) {
            Button(
                onClick = {
                    showRemoveConfirm = true
                }, // When clicked, show the confirmation dialog for removal
                modifier = Modifier
                    .fillMaxWidth() // Make the button fill the available width
                    .padding(top = 8.dp), // Add a top margin to this button
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error // Use an error (red) color for the button
                ),
                shape = RoundedCornerShape(12.dp) // Set rounded corners for the button
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Remove Carer"
                ) // Display delete icon
                Spacer(Modifier.width(8.dp)) // Add space between icon and text
                Text("Remove Carer") // Button label text
            }
        }

        Spacer(Modifier.height(16.dp)) // Adds a smaller vertical space between sections

// Display a message if it is not empty (e.g., assignment/removal success message)
        if (message.isNotEmpty()) {
            Text(
                text = message, // Display the message
                style = MaterialTheme.typography.bodyLarge, // Style it with large body text
                color = MaterialTheme.colorScheme.primary // Use primary color for the message
            )
        }

// Confirmation dialog to assign a carer to a client
        if (showAssignConfirm) {
            ConfirmDialog(
                title = "Confirm Assignment", // Dialog title
                text = "Assign ${selectedCarer?.name} to ${selectedClient?.name}?", // Dialog message
                onConfirm = {
                    scope.launch {
                        selectedClient?.let { client ->
                            selectedCarer?.let { carer ->
                                userViewModel.assignCarerToClient(client.clientId, carer.userId)
                                message = "Assigned ${carer.name} to ${client.name}"
                            }
                        }
                        showAssignConfirm = false
                    }
                }
                ,
                onDismiss = { showAssignConfirm = false } // Close the dialog if dismissed
            )
        }

// Confirmation dialog to remove a carer from a client
        if (showRemoveConfirm) {
            ConfirmDialog(
                title = "Confirm Removal", // Dialog title
                text = "Remove carer from ${selectedClient?.name}?", // Dialog message
                onConfirm = { // Action to take when "Confirm" is pressed
                    scope.launch {
                        selectedClient?.carerId = null // Remove the carer ID from the client
                        selectedClient?.let {
                           // clientRepository.updateClient(it) // Use the repository to update the client data in the database
                            message =
                                "Removed carer from ${selectedClient?.name}" // Success message
                        }
                        showRemoveConfirm = false // Close the confirmation dialog
                    }
                },
                onDismiss = { showRemoveConfirm = false } // Close the dialog if dismissed
            )
        }
    }
}