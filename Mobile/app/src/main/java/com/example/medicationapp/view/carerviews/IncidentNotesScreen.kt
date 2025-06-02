package com.example.medicationapp.view.carer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Composable screen for entering and submitting incident notes.
 * - Displays a text input field for the user to type notes.
 * - Includes a submit button (currently without implemented action).
 * - Uses a Scaffold with a centered top app bar titled "Upload Incident Notes".
 * - Layout is padded and spaced for user-friendly input.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidentNotesScreen() {
    var notes by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Upload Incident Notes") }
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
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Incident Notes") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {

                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Submit")
            }
        }
    }
}
