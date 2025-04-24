package com.example.medicationapp.view.carer

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.medicationapp.controller.ReminderController
import com.example.medicationapp.model.Reminder
import kotlinx.coroutines.launch
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RemindersScreen(reminderController: ReminderController) {
    val coroutineScope = rememberCoroutineScope()
    var reminders by remember { mutableStateOf<List<Reminder>>(emptyList()) }
    var reminderType by remember { mutableStateOf(TextFieldValue()) }
    var reminderTime by remember { mutableStateOf(LocalTime.now()) }
    var clientMedicationId by remember { mutableStateOf(0L) }

    // Load reminders
    LaunchedEffect(Unit) {
        reminders = reminderController.getAllReminders()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Medication Reminders") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Here you can view and manage medication reminders.")

            // List of Reminders
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(reminders) { reminder ->
                    ReminderCard(
                        reminder = reminder,
                        onUpdate = {
                            coroutineScope.launch {
                                reminderController.updateReminder(it)
                                reminders = reminderController.getAllReminders()
                            }
                        },
                        onDelete = {
                            coroutineScope.launch {
                                reminderController.deleteReminder(it)
                                reminders = reminderController.getAllReminders()
                            }
                        }
                    )
                }
            }

            // Add New Reminder
            TextField(
                value = reminderType,
                onValueChange = { reminderType = it },
                label = { Text("Reminder Type") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = reminderTime.toString(),
                onValueChange = {
                    try {
                        reminderTime = LocalTime.parse(it)
                    } catch (e: Exception) { /* ignore parse error */ }
                },
                label = { Text("Reminder Time (HH:mm)") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        val newReminder = Reminder(
                            clientMedicationId = clientMedicationId,
                            reminderType = reminderType.text,
                            reminderTime = reminderTime
                        )
                        reminderController.createReminder(newReminder)
                        reminderType = TextFieldValue()
                        reminders = reminderController.getAllReminders()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Reminder")
            }
        }
    }
}

@Composable
fun ReminderCard(
    reminder: Reminder,
    onUpdate: (Reminder) -> Unit,
    onDelete: (Reminder) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Reminder Type: ${reminder.reminderType}")
            Text("Time: ${reminder.reminderTime}")
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { onUpdate(reminder) }) {
                    Text("Update")
                }
                Button(onClick = { onDelete(reminder) }) {
                    Text("Delete")
                }
            }
        }
    }
}
