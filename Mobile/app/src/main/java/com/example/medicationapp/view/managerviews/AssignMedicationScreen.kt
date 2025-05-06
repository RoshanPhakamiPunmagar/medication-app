package com.example.medicationapp.view.managerviews

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.medicationapp.view.alarm.AlarmScheduler
import com.example.medicationapp.model.Client
import com.example.medicationapp.model.ClientMedication
import com.example.medicationapp.model.Medication
import com.example.medicationapp.view.TimeWheelPickerDialog
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import com.example.medicationapp.model.repository.ClientRepository
import com.example.medicationapp.repository.MedicationRepository


@SuppressLint("ScheduleExactAlarm")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignMedicationScreen(
    clientRepository: ClientRepository,
    medicationRepository: MedicationRepository
)
 {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var clients by remember { mutableStateOf<List<Client>>(emptyList()) }
    var medications by remember { mutableStateOf<List<Medication>>(emptyList()) }

    var selectedClient by remember { mutableStateOf<Client?>(null) }
    var selectedMedication by remember { mutableStateOf<Medication?>(null) }

    var dosage by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var scheduledTimes by remember { mutableStateOf<List<LocalTime>>(emptyList()) }

    var successMessage by remember { mutableStateOf<String?>(null) }
    var schedules by remember { mutableStateOf<List<ClientMedication>>(emptyList()) }

    var showTimePicker by remember { mutableStateOf(false) }


    var clientMedication by remember { mutableStateOf<ClientMedication?>(null) }



    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val datePickerStateStart = rememberDatePickerState()
    val datePickerStateEnd = rememberDatePickerState()
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        clients = clientRepository.getAllClients()
        medications = medicationRepository.getAllMedications()

    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    )
    {
        Text("Assign Medication", style = MaterialTheme.typography.headlineMedium)

        // --- Client Dropdown ---
        var clientExpanded by remember { mutableStateOf(false) } // State variable to track if the client dropdown menu is expanded or not

// TextField for selecting a client
        TextField(
            value = selectedClient?.name
                ?: "", // If a client is selected, display their name, otherwise show an empty string
            onValueChange = {}, // No need to change value since it's read-only
            readOnly = true, // Make the TextField read-only so the user cannot type into it
            label = { Text("Select Client") }, // Label displayed in the TextField
            modifier = Modifier.fillMaxWidth(), // Make the TextField take up full width
            trailingIcon = { // Icon button to toggle the dropdown menu
                IconButton(onClick = {
                    clientExpanded = !clientExpanded
                }) { // Toggle the state to expand or collapse the menu
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null
                    ) // Show the drop-down icon
                }
            }
        )

// DropdownMenu for showing the list of clients when the user clicks the icon
        DropdownMenu(
            expanded = clientExpanded, // Show the menu if clientExpanded is true
            onDismissRequest = {
                clientExpanded = false
            }, // Hide the menu when the user clicks outside
            modifier = Modifier.heightIn(max = 300.dp) // Set a maximum height for the dropdown menu
        ) {
            // Iterate over the list of clients and display each one in a menu item
            clients.forEach { client ->
                DropdownMenuItem(
                    text = { Text(client.name) }, // Display the client's name
                    onClick = {
                        selectedClient = client // Set the selected client when clicked
                        clientExpanded = false // Close the dropdown after selecting
                    }
                )
            }
        }

// --- Medication Dropdown ---
        var medicationExpanded by remember { mutableStateOf(false) } // State variable to track if the medication dropdown menu is expanded or not

// TextField for selecting a medication
        TextField(
            value = selectedMedication?.name
                ?: "", // If a medication is selected, display its name, otherwise show an empty string
            onValueChange = {}, // No need to change value since it's read-only
            readOnly = true, // Make the TextField read-only so the user cannot type into it
            label = { Text("Select Medication") }, // Label displayed in the TextField
            modifier = Modifier.fillMaxWidth(), // Make the TextField take up full width
            trailingIcon = { // Icon button to toggle the dropdown menu
                IconButton(onClick = {
                    medicationExpanded = !medicationExpanded
                }) { // Toggle the state to expand or collapse the menu
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null
                    ) // Show the drop-down icon
                }
            }
        )

// DropdownMenu for showing the list of medications when the user clicks the icon
        DropdownMenu(
            expanded = medicationExpanded, // Show the menu if medicationExpanded is true
            onDismissRequest = {
                medicationExpanded = false
            }, // Hide the menu when the user clicks outside
            modifier = Modifier.heightIn(max = 300.dp) // Set a maximum height for the dropdown menu
        ) {
            // Iterate over the list of medications and display each one in a menu item
            medications.forEach { med ->
                DropdownMenuItem(
                    text = { Text(med.name) }, // Display the medication's name
                    onClick = {
                        selectedMedication = med // Set the selected medication when clicked
                        medicationExpanded = false // Close the dropdown after selecting
                    }
                )
            }
        }


        //Inputs
        TextField(
            value = dosage,
            onValueChange = { dosage = it },
            label = { Text("Dosage") },
            modifier = Modifier.fillMaxWidth()
        )

        // Start Date Picker Trigger
        Button(
            onClick = { showStartDatePicker = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (startDate.isBlank()) "Select Start Date" else "Start Date: $startDate")
        }

        // End Date Picker Trigger
        Button(
            onClick = { showEndDatePicker = true },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text(if (endDate.isBlank()) "Select End Date" else "End Date: $endDate")
        }

        //Scheduled Times
        Text("Scheduled Times:", style = MaterialTheme.typography.titleMedium)
        if (scheduledTimes.isEmpty()) {
            Text("No times added yet.")
        } else {
            scheduledTimes.sorted().forEach { time ->
                Text(time.toString(), style = MaterialTheme.typography.bodyLarge)
            }
        }

        Button(
            onClick = { showTimePicker = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Scheduled Time")
        }

        // Save Button
        Button(
            onClick = {
                // Launching a coroutine for the background task of saving the medication assignment
                coroutineScope.launch {
                    try {
                        // Parse the start date and end date from the string input into LocalDate objects
                        val parsedStart = LocalDate.parse(startDate)
                        val parsedEnd = LocalDate.parse(endDate)

                        // Define a time formatter for "HH:mm" format
                        val formatter = DateTimeFormatter.ofPattern("HH:mm")
                        // Parse all scheduled times into LocalTime objects
                        val timeFormated: List<LocalTime> = scheduledTimes.mapNotNull { timeStr ->
                            try {
                                Log.e(
                                    "TimeParse",
                                    "Success to parse $timeStr"
                                )  // Log successful parsing
                                LocalTime.parse(timeStr.toString(), formatter)
                            } catch (e: Exception) {
                                Log.e(
                                    "TimeParse",
                                    "Failed to parse $timeStr",
                                    e
                                )  // Log failed parsing attempt
                                null // If parsing fails, skip the invalid time
                            }
                        }

                        // If no valid times were parsed, show an error message to the user
                        if (timeFormated.isEmpty()) {
                            Toast.makeText(context, "Please enter valid times", Toast.LENGTH_SHORT)
                                .show()
                        }

                        // If a valid client and medication are selected, and we have valid dates and times
                        selectedClient?.clientId?.let { clientId ->
                            selectedMedication?.medicationId?.let { medicationId ->
                                // Call the repository function to assign medication to the client
                                clientMedication = medicationRepository.assignMedicationToClient(
                                    clientId = clientId,
                                    medicationId = medicationId.toLong(),
                                    dosage = dosage,
                                    startDate = parsedStart,
                                    endDate = parsedEnd,
                                    scheduledTimes = timeFormated
                                )

                                // Log the ID of the assigned medication for debugging
                                Log.d(
                                    "AlarmCheck",
                                    "clientMedication is ${clientMedication?.clientMedicationId}"
                                )

                                // If medication assignment is successful, set up alarms for the medication schedule


                                // Show success message after assigning medication
                                successMessage = "Medication assigned successfully!"
                            }
                        }
                    } catch (e: DateTimeParseException) {
                        // Handle invalid date format exception
                        successMessage = "Invalid date format. Use yyyy-MM-dd."
                    } catch (e: Exception) {
                        // Handle any other exceptions that might occur
                        successMessage = "Error: ${e.message}"
                    }
                }
            },
            enabled = selectedClient != null && selectedMedication != null &&
                    dosage.isNotBlank() &&
                    startDate.isNotBlank() && endDate.isNotBlank(), // Enable the button if all required fields are filled
            modifier = Modifier.fillMaxWidth() // Make the button take up the full width of its parent container
        ) {
            Text("Assign Medication") // The button label
        }

// Clear All Button
        Button(
            onClick = {
                // Reset all input fields and state variables to their initial values
                selectedClient = null
                selectedMedication = null
                dosage = ""
                startDate = ""
                endDate = ""
                scheduledTimes = emptyList()
                successMessage = null
            },
            modifier = Modifier.fillMaxWidth(), // Make the button take up the full width of its parent container
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error) // Use an error color for the button
        ) {
            Text(
                "Clear All",
                color = MaterialTheme.colorScheme.onError
            ) // The button label with text color for error state
        }

// Show success message if it's available
        successMessage?.let {
            // Display the success message in the primary color theme
            Text(it, color = MaterialTheme.colorScheme.primary)
        }

// --- Existing Schedules ---// NEED TO DO THIS ...
        if (schedules.isNotEmpty()) {  // Check if there are any existing schedules to display
            Text(
                "Current Schedules",
                style = MaterialTheme.typography.titleMedium
            ) // Heading for the current schedules section

            // Iterate over all the existing schedules and display them in a Card view
            schedules.forEach { schedule ->
                Card(
                    modifier = Modifier.fillMaxWidth(), // Make the Card take up the full width
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant) // Set a surface variant color for the card
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        // Display details of each schedule: medication, dosage, start date, and end date
                        Text("Medication: ${schedule.medicationId}")
                        Text("Dosage: ${schedule.dosage}")
                        Text("Start Date: ${schedule.startDate}")
                        Text("End Date: ${schedule.endDate}")
                    }
                }
            }
        }
// --- Use TimeWheelPickerDialog instead of text input ---
        if (showTimePicker) {
            // Display the TimeWheelPickerDialog when 'showTimePicker' is true
            TimeWheelPickerDialog(
                onDismiss = {
                    // Close the time picker dialog when dismissed
                    showTimePicker = false
                },
                onTimeSelected = { hour, minute ->
                    // When a time is selected, construct a LocalTime object using the hour and minute
                    val time = LocalTime.of(hour, minute)
                    // Add the selected time to the scheduledTimes list
                    scheduledTimes = scheduledTimes + time
                    // Close the time picker dialog after selecting the time
                    showTimePicker = false
                }
            )
        }

// Start DatePicker Dialog
        if (showStartDatePicker) {
            // Display the DatePickerDialog for the start date when 'showStartDatePicker' is true
            DatePickerDialog(
                onDismissRequest = {
                    // Close the start date picker when dismissed
                    showStartDatePicker = false
                },
                confirmButton = {
                    // Confirmation button to select the start date
                    TextButton(onClick = {
                        // Get the selected date in milliseconds from the date picker state
                        val millis = datePickerStateStart.selectedDateMillis
                        if (millis != null) {
                            // Convert milliseconds to LocalDate using ofEpochDay method
                            val date = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
                            // Format the selected date into the desired string format
                            startDate = date.format(dateFormatter)
                        }
                        // Close the start date picker dialog after selection
                        showStartDatePicker = false
                    }) {
                        Text("OK") // OK button text to confirm the selection
                    }
                },
                dismissButton = {
                    // Dismiss button to close the date picker without making any changes
                    TextButton(onClick = { showStartDatePicker = false }) {
                        Text("Cancel") // Cancel button text to discard the selection
                    }
                }
            ) {
                // Display the DatePicker component for the start date
                DatePicker(state = datePickerStateStart)
            }
        }

// End DatePicker Dialog
        if (showEndDatePicker) {
            // Display the DatePickerDialog for the end date when 'showEndDatePicker' is true
            DatePickerDialog(
                onDismissRequest = {
                    // Close the end date picker when dismissed
                    showEndDatePicker = false
                },
                confirmButton = {
                    // Confirmation button to select the end date
                    TextButton(onClick = {
                        // Get the selected date in milliseconds from the date picker state
                        val millis = datePickerStateEnd.selectedDateMillis
                        if (millis != null) {
                            // Convert milliseconds to LocalDate using ofEpochDay method
                            val date = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
                            // Format the selected date into the desired string format
                            endDate = date.format(dateFormatter)
                        }
                        // Close the end date picker dialog after selection
                        showEndDatePicker = false
                    }) {
                        Text("OK") // OK button text to confirm the selection
                    }
                },
                dismissButton = {
                    // Dismiss button to close the date picker without making any changes
                    TextButton(onClick = { showEndDatePicker = false }) {
                        Text("Cancel") // Cancel button text to discard the selection
                    }
                }
            ) {
                // Display the DatePicker component for the end date
                DatePicker(state = datePickerStateEnd)
            }
        }
    }}
