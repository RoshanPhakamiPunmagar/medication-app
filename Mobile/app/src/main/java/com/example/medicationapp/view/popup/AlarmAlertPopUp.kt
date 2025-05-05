package com.example.medicationapp.view.popup

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.medicationapp.controller.ClientController
import com.example.medicationapp.controller.MedicationController
import com.example.medicationapp.view.alarm.AlarmConfig
import com.example.medicationapp.view.alarm.getClientMedication
import com.example.medicationapp.model.Client
import com.example.medicationapp.model.ClientMedication
import com.example.medicationapp.model.Medication

@Composable
fun AlarmAlertPopUp (
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                    AlarmConfig.stop()
                }
            ) {
                Text("Taken")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    AlarmConfig.stop()
                    onConfirmation()
                }
            ) {
                Text("Skip")
            }
        }
    )
}

@Composable
fun AlarmDialogScreen(onDismiss: () -> Unit) {
    val context = LocalContext.current
    val clientController = ClientController(context)
    val medicationController = MedicationController(context)

    val clientMedication: ClientMedication? = getClientMedication()

    var client by rememberSaveable { mutableStateOf<Client?>(null) }
    var meds by rememberSaveable { mutableStateOf<Medication?>(null) }


    LaunchedEffect(Unit) {
        clientMedication?.let {
            meds = medicationController.getMedications(it.medicationId)
            client = clientController.getClientById(it.clientId)
            // Use the medication data here
        } ?: run {
            // Handle the case when clientMedication is null, e.g., show an error or default behavior
            Log.d("Alarm", "Client medication is null.")
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            AlarmAlertPopUp(
                onDismissRequest = onDismiss,
                onConfirmation = onDismiss,
                dialogTitle = "Reminder for ${client?.name}",
                dialogText = "Medication Name:  ${meds?.name} \n Dosage : ${clientMedication?.dosage}",

                icon = Icons.Default.Done
            )

    }
}